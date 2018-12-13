var projectName = getProjectName();

$(function () {

    serverSide({
        hierarchyLevel: 1,
        modulePath: "/group",
        methodPath: "/projects/?/groups",
        pathParams: [projectName],
        queryParams: []
    }, "GET", printGroups);

    $(".sortableGroup").disableSelection();

    $("#persistNewGroups").click(function () {

        serverSide({
            hierarchyLevel: 1,
            modulePath: "/group",
            methodPath: "/projects/?/groups",
            pathParams: [projectName],
            queryParams: [],
            entity: getGroupDataFromHtml()
        }, "PUT", function (a, b, c) {
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

    function printGroups(a, b, c) {
        var firstgroup =
            groups = {
                bla: [
                    {
                        id: "1",
                        members: [{name: "me", email: "egal@stuff.com"}, {
                            name: "him",
                            email: "egal@stuff.com"
                        }, {name: "her", email: "egal@stuff.com"}]
                    },
                    {
                        id: "2",
                        members: [{name: "me2", email: "egal@stuff.com"}, {
                            name: "him2",
                            email: "egal@stuff.com"
                        }, {name: "her2", email: "egal@stuff.com"}]
                    }
                ],
                lastGroupId: "44"
            };

        // create a group
        $("#groupTemplate")
            .tmpl(firstgroup)
            .appendTo("#editable_groups");

        $("ul.droptrue").sortable({
            connectWith: "ul"
        });

    }
});
