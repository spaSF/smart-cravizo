select * from sc_datasource order by metaid;
select * from sc_dsfield order by xdatasource, id;
select * from sc_operation order by xdatasource, id;
select * from sc_oper_param order by xoperation, id;
select * from sc_config order by id;
select * from sc_menuitem order by id;
select * from sc_request_map order by id;

select * from sc_role order by id;
select * from sc_role_group order by id;
select * from sc_role_group_role order by role_group_id, role_id;
