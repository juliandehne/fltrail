package unipotsdam.gf.modules.fileManagement;

import org.junit.Test;

public class FileManagementServiceTest {

    @Test
    public void manipulateIndentation() {
        String fileContent = "<h1>kekse</h1><p><br></p><p>Das ist ein beispieltext um zu testen, wie man intentation fixen kann.</p><p>Ich mag zuege sehr abcdefg</p><p><br></p><ol><li>1</li><li class=\"ql-indent-1\">a</li><li class=\"ql-indent-1\">b</li><li>2</li><li class=\"ql-indent-2\">i</li><li class=\"ql-indent-1\">bcd</li><li class=\"ql-indent-1\">abcd</li><li class=\"ql-indent-2\">i</li><li class=\"ql-indent-2\">ii</li><li class=\"ql-indent-2\">iii</li><li class=\"ql-indent-2\">iv</li><li class=\"ql-indent-1\">efgh</li><li class=\"ql-indent-2\">i</li><li class=\"ql-indent-2\">ii</li><li class=\"ql-indent-3\">1innen</li><li class=\"ql-indent-4\">ainnen</li><li class=\"ql-indent-3\">2innen</li><li class=\"ql-indent-4\">binnen</li></ol>";
        FileManagementService fileManagementService = new FileManagementService();

        fileManagementService.manipulateIndentation(fileContent);

    }
}