$(function() {

    $("#sendCommand").click(function() {
        
        if ($("#command").val() == "") {
        	
            $("#commandValidation").html("O campo comando é obrigatório!");
                
        } else {
        	
        	DistributedDataBaseService.execute($("#command").val()).done(executeOk).fail(executeFailed);
        	
        }

    });
    
    $("#connect").click(function() {
    	
    	DistributedDataBaseService.open().done(connectedOk).fail(connectionFailed);

    });
    
    $("#disconnect").click(function() {
    	
        DistributedDataBaseService.close().done(disconnectedOk).fail(disconnectionFailed);;

    });
    
    $("#disconnect").prop("disabled",true);
    $("#command").prop("disabled",true);
    $("#sendCommand").prop("disabled",true);
    
});

function connectedOk(data) {
	
	limparTela();
	
    $("#disconnect").prop("disabled",false);
    $("#command").prop("disabled",false);
    $("#sendCommand").prop("disabled",false);
    $("#connect").prop("disabled",true);    
    
}

function disconnectedOk(data) {
	
	limparTela();
	
    $("#disconnect").prop("disabled",true);
    $("#command").prop("disabled",true);
    $("#sendCommand").prop("disabled",true);
    $("#connect").prop("disabled",false);
    
}

function executeOk(data) {
	
	limparTela();
	
    $("#commandValidation").html("Comando executado com sucesso [" + data + "]");
}

function executeFailed(request) {
    $("#commandValidation").text("Erro ao cadastrar mensagem!");
}

function connectionFailed(request) {
    $("#commandValidation").text("Erro ao realizar conectar no JGroups!");
}

function disconnectionFailed(request) {
    $("#commandValidation").text("Erro ao desconectar no JGroups!");
}

function limparTela() {
	$("#commandValidation").html("");
    $("#command").val("");
}