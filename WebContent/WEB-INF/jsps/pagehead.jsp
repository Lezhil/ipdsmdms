

	<script>	
	function loadPage()
	{		
		var user='<%=(String)session.getAttribute("userType") %>';			
		if(user=='admin' || user=='ADMIN')
		{	
			window.location.href="./adminDashboard";
		}
		else
		{			
			window.location.href="./userProjectDashboard";
		}
	}


	function loadPageContent(param)
	{		
		var loadUrl = "./"+param;
		$("#pagecontainer").load(loadUrl);
	}
	
	function loadaction(param)
	{	
	if(param == 'dashMenu')
	{	
	window.location.href="./adminDashboard";
	
	}
	
	if(param == 'addCategory')
	{	 
	window.location.href="./addCategory";
 
	}	
	
	
	if(param == 'manageusers')
	{
	 
	 window.location.href="./manageUser";
	}
	
	if(param == 'manageHirarchy')
	{
	 
	 window.location.href="./ManageHierarchies"; 
	}
	
	if(param == 'addLocation')
		{
		
		window.location.href="./addLocation";		
		}
	
	if(param == 'smsgateway')
	{
	
	window.location.href="./SMSGatewaySettings";	
	}
	
	if(param == 'emailgateway')
	{
	
	window.location.href="./EmailGatewaySettings";	
	}
	
	if(param == 'userAccessmanage')
	{
	
	window.location.href="./userAccessManagement";	
	
	}
	
	if(param == 'ActiveDirectory')
	{
	
	window.location.href="./ActiveDirectorySettings";	
	}
	
	if(param == 'stageAndSubstage')
	{
	
	window.location.href="./ManageStagesAndSubStages";	
	}
	
	if(param == 'manageProjects')
	{			
	window.location.href="./ManageProjects";	
	
	}
	
	if(param == 'manageDepartment')
	{	
		
	window.location.href="./ManageDepartments";	
	
	}
	
	if(param == 'userdashMenu')
	{
	window.location.href="./userProjectDashboard";	
	}
	
	if(param == 'userProjectStatus')
	{
	  window.location.href="./userProjectStatusTracking";	
	}
	
	if(param == 'userDailyLog')
	{
	
	window.location.href="./userDailyLog";		
	}
	
	if(param == 'userScheduleProjects')
	{
	
	window.location.href="./userScheduleProjects";	
	}
	
	if(param == 'userStatusUpdates')
	{
	
	$('#userStatus-Updates').addClass('start active ,selected');
	$('#user-dash,#userProject-Status,#userDaily-Log,#user-Schedule').removeClass('start active ,selected');
	window.location.href="./userStatusUpdates";	
	}
	
	if(param == 'home')
	{
	
	$('#event-info').addClass('start active ,selected');
	$('#transaction-info,#dash,#generalinfo,#readings').removeClass('start active ,selected');
	window.location.href="./userHome";
	
	
	}
	
	if(param == 'logout')
	{	
	  window.location.href = "./logout";
	
	}
	if(param == 'my-profile')
	{
		alert('hoi');
		window.location.href="./profile";	
	
	}
	if(param == 'my-task')
	{
		window.location.href="./taskList";	
	
	}
	if(param == 'my-schedule')
	{
		window.location.href="./myschedule";	
	}
	if(param == 'daily-logs')
	{
		window.location.href="./dailylogs";	
	}
	if(param == 'lock-screen')
	{	
		window.location.href = "./lockScreen";	
	}
	
	}
	
	</script>
	

