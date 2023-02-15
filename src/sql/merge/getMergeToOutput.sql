CREATE OR REPLACE PROCEDURE getmergetooutput(
    intabname  IN VARCHAR2
   ,inorderby  IN VARCHAR2 DEFAULT 'id'
   ,inmergetby IN VARCHAR2 DEFAULT 'id'
   ,inwhere    IN VARCHAR2 DEFAULT NULL
   ,inCommit   IN NUMBER   DEFAULT 1) AS
    stmt       CLOB;
    ignor      NUMBER;
    src_cursor NUMBER;
    fld_count  NUMBER DEFAULT 0;
    v_tab      dbms_sql.desc_tab2;
    mnumber    NUMBER;
    mchar      VARCHAR2(4000);
    mPKCols    VARCHAR2(4000) := ',' || upper( regexp_replace(nvl(inmergetby,'id'), '\s+', '')) || ',';
    mtimestamp TIMESTAMP;
    mcolsnm_b  VARCHAR2(4000);
    mupdateset VARCHAR2(4000);
    cnt        NUMBER := 0;
    err        VARCHAR2(2000);
    printLn    VARCHAR2(2000);
    mprompt    VARCHAR2(256);
    delim      VARCHAR2(3);
BEGIN
    stmt       := 'select * from ' || intabname;
    IF inwhere IS NOT NULL THEN
        stmt := stmt || ' where ' || inwhere || 'is not null';
    END IF;
    stmt       := stmt || ' order by ' || inorderby;
    dbms_output.put_line('-- ' || stmt);
    src_cursor := dbms_sql.open_cursor;
    dbms_sql.parse(src_cursor, stmt, dbms_sql.native);
    dbms_sql.describe_columns2(src_cursor, fld_count, v_tab); -- get record structure
    FOR i IN 1 .. fld_count LOOP
        -- DBMS_OUTPUT.PUT_LINE('Col ' || I || ' Type:' || v_tab(I).COL_TYPE);
        -- DBMS_OUTPUT.PUT_LINE('Col ' || I || ' Name:' || v_tab(I).COL_NAME);
        err       := ' Name:' || v_tab(i).col_name || ' Type:' || v_tab(i).col_type;
        cnt       := cnt + 1;
        IF upper(v_tab(i).col_name) != v_tab(i).col_name then
            v_tab(i).col_name := '"' || v_tab(i).col_name || '"';
        END IF;
        mcolsnm_b := mcolsnm_b || 'b.' || v_tab(i).col_name || ', ';
        IF cnt > 3 THEN
            mcolsnm_b := mcolsnm_b || chr(10) || chr(9);
            cnt       := 0;
        END IF;
        IF instr( mPKCols, upper(','||v_tab(i).col_name||',')) = 0 THEN
            mupdateset := mupdateset || chr(9) || 'a.' || v_tab(i).col_name || ' = ' || 'b.' || v_tab(i).col_name || ',' || chr(10);
       END IF;
        CASE v_tab(i).col_type
            WHEN 2 THEN
                dbms_sql.define_column(src_cursor, i, mnumber);
            WHEN 180 THEN
                dbms_sql.define_column(src_cursor, i, mtimestamp);
            WHEN 1 THEN
                dbms_sql.define_column(src_cursor, i, mchar, 4000);
            WHEN 112 THEN
                dbms_sql.define_column(src_cursor, i, mchar, 4000);
        END CASE;
        NULL;
    END LOOP;
    ignor      := dbms_sql.execute(src_cursor);
    dbms_output.put_line('SET DEFINE OFF;');
    IF inCommit > 1 THEN
        dbms_output.put_line('begin setConstraintsForMerge.setDisableConstr(''' || intabname || ''',''R''); end;');
        dbms_output.put_line('/');
    END IF;
    LOOP
        IF dbms_sql.fetch_rows(src_cursor) > 0 THEN
            dbms_output.put_line('MERGE INTO ' || intabname || ' A USING');
            dbms_output.put_line(chr(9) || '(SELECT');
            delim := ',';
            FOR i IN 1 .. fld_count LOOP
                IF i = fld_count THEN
                    delim := NULL;
                END IF;
                IF v_tab(i).col_type IN (1) THEN
                    dbms_sql.column_value(src_cursor, i, mchar);
                    mchar := replace(replace(mchar,chr(10)),chr(13));
                    IF mchar IS NULL THEN
                        printLn := chr(9) || 'null as ' || v_tab(i).col_name || delim;
                    ELSE
                        printLn := chr(9) || 'q''[' || mchar || ']'' as ' || v_tab(i).col_name || delim;
                    END IF;
                END IF;
                IF v_tab(i).col_type IN (2) THEN
                    dbms_sql.column_value(src_cursor, i, mnumber);
                    IF mnumber IS NULL THEN
                        printLn := chr(9) || 'null as ' || v_tab(i).col_name || delim;
                    ELSE
                        printLn := chr(9) ||  mnumber || ' as ' || v_tab(i).col_name || delim;
                    END IF;
                END IF;
                IF v_tab(i).col_type IN (180) THEN
                    dbms_sql.column_value(src_cursor, i, mtimestamp);
                    IF mtimestamp IS NULL THEN
                        printLn := chr(9) || 'null as ' || v_tab(i).col_name || delim;
                    ELSE
                        printLn :=
                            chr(9) || 'TO_TIMESTAMP('''
                            || to_char(mtimestamp, 'fmDDfm. fmMMfm. YYYY fmHH24fm:MI:SS,FF')
                            || ''',''fmDDfm. fmMMfm. YYYY fmHH24fm:MI:SS,FF'') as '
                            || v_tab(i).col_name
                            || delim;
                    END IF;
                END IF;
                IF v_tab(i).col_type IN (112) THEN
                    printLn := chr(9) || 'null as ' || v_tab(i).col_name || delim;
                END IF;
                dbms_output.put_line(printLn);
                IF i = 1 THEN
                    mprompt := printLn;
                END IF;
            END LOOP;
            dbms_output.put_line(chr(9) || 'FROM DUAL) B');
            -- dbms_output.put_line('ON (a.' || nvl(inmergetby,'id') || ' = b.' || nvl(inmergetby,'id') || ')');
            mPKCols := 'ON (a.' || regexp_substr(nvl(inmergetby,'id'),'[^,]+',1,1)  || ' = b.' || regexp_substr(nvl(inmergetby,'id'),'[^,]+',1,1);
            for icnt in 1 .. regexp_count( nvl(inmergetby,'id'),',',1,'i') loop
                mPKCols := mPKCols || ' and a.' || regexp_substr(nvl(inmergetby,'id'),'[^,]+',1,icnt+1)  || ' = b.' || regexp_substr(nvl(inmergetby,'id'),'[^,]+',1,icnt+1);
            end loop;
            dbms_output.put_line(mPKCols || ')');
            dbms_output.put_line('WHEN NOT MATCHED THEN ');
            dbms_output.put_line('INSERT (');
            dbms_output.put_line(chr(9) || rtrim(trim(BOTH FROM replace(mcolsnm_b, 'b.')), ', ' || chr(10) || chr(9)) || ')');
            dbms_output.put_line('VALUES (');
            dbms_output.put_line(chr(9) || rtrim(trim(BOTH FROM (mcolsnm_b)), ', ' || chr(10) || chr(9)) || ')');
            if length(mupdateset) > 0 then
                mprompt := 'prompt -- ' || intabname || ' - ' || mprompt || ' --;';
                dbms_output.put_line('WHEN MATCHED THEN');
                dbms_output.put_line('UPDATE SET');
                dbms_output.put_line(rtrim(mupdateset, ',' || chr(10)) || ';' || chr(10) || mprompt);
            else
                dbms_output.put_line(';');
            end if;
        ELSE
            EXIT;
        END IF;
    END LOOP;
    IF inCommit > 1 THEN
        dbms_output.put_line('begin setConstraintsForMerge.setEnableConstr; end;');
        dbms_output.put_line('/');
    ELSIF inCommit = 1 Then
        dbms_output.put_line('commit;');
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        dbms_output.put_line(err);
        dbms_output.put_line(dbms_utility.format_error_stack);
END;
/