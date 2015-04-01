var DistributedDataBaseService = {

	execute : function(command) {
	
		urlCommand = "api/database/command/" + command;
		
        return $.ajax({
                url : urlCommand
        });
        
	},

	open : function() {
		
		urlCommand = "api/database/connection/open";
		
        return $.ajax({
                url : urlCommand
        });
	    
	},
	
	close : function() {
		
		urlCommand = "api/database/connection/close";
		
	    return $.ajax({
	            url : urlCommand
	    });
	    
	}
	
};