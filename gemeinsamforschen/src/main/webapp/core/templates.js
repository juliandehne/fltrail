Templates={};

Templates.projectTableTR =[
    '<tr class="pageChanger">',
    '   <td>',
    '       <a id="project&{projectName}">',
    '       <h1>${projectName}</h1>',
    '       </a>',
    '   </td>',
    '</tr>',
    '<tr>',
    '   <td>',
    '       <div class="panel panel-default">',
    '           <div class="panel-heading">',
    '               <h3 class="panel-title">Newsfeed </h3>',
    '               Status: <p id="status${projectName}"></p>',
    '           </div>',
    '           <div class="panel-body">',
    '               <ul class="list-group">',
    '                       <li class="list-group-item">',
    '                           <span>dummy</span>',
    '                       </li>',
    '                       <li class="list-group-item">' ,
    '                           <span>dummy</span>',
    '                       </li>',
    '                       <li class="list-group-item">',
    '                           <span>dummy</span></li>',
    '              </ul>',
    '          </div>',
    '      </div>',
    '   </td>',
    '</tr>',
    '<tr>',
    '   <td></td>',
    '</tr>'
].join("\n");
