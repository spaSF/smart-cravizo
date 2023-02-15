// Created by the Smart SFS plugin>>>>>
class UrlMappings {

		static mappings = {
			"/$controller/$action?/$id?(.$format)?"{
				constraints {
					// apply constraints here
				}
			}

			"500"(view:"/error")
			"/"(view:"/index")//(controller:"SmartNavigator",action:"index")
			"/index.gsp"(view:"/index")
			"/home"(controller:"SmartNavigator",action:"home")
			"/error"(controller:"SmartNavigator",action:"error")
		}
	}
