
$(function () {

    var projectName = getProjectName();

    serverSide({
        hierarchyLevel:1,
        modulePath:"/group",
        methodPath: "/all/projects/?",
        pathParams: [projectName],
        queryParams: []
    },"GET", printGroups);

    $(".sortableGroup").disableSelection();

    $("#persistNewGroups").click(function () {

        serverSide({
            hierarchyLevel: 1,
            modulePath: "/group",
            methodPath: "/projects/?/groups",
            pathParams: [projectName],
            queryParams: [],
            entity: getGroupDataFromHtml()
        }, "PUT", function (a,b,c) {
            console.log(a);
        })
    });
    $("#finalizeNewGroups").click(function () {
        // TODO get firstgroup
    });

    function getGroupDataFromHtml() {
        var i = 1;
        //$("#group_1 > li > table > tbody > tr > td:nth-child(2)")
    }

    function printGroups(a,b,c) {
        var data = a;

        // create a group
        $("#groupTemplate")
            .tmpl(data)
            .appendTo("#editable_groups");

        $("ul.droptrue").sortable({
            connectWith: "ul"
        });

    }
});
