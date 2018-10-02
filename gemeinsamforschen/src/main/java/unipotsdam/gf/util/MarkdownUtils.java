package unipotsdam.gf.util;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

/**
 * Utility class to convert markdown to html and back
 */

public class MarkdownUtils {

    /**
     * Converts a markdown text to html
     * @param markdown markdown text
     * @return html text
     */
    public static String convertMarkdownToHtml(String markdown){
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

}
