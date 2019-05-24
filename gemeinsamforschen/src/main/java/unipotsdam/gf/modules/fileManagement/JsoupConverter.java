package unipotsdam.gf.modules.fileManagement;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import unipotsdam.gf.modules.fileManagement.Util.IndentationLetterFormat;
import unipotsdam.gf.modules.fileManagement.Util.RomanConverter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static unipotsdam.gf.modules.fileManagement.Util.IndentationLetterFormat.LETTER;
import static unipotsdam.gf.modules.fileManagement.Util.IndentationLetterFormat.NUMBER;
import static unipotsdam.gf.modules.fileManagement.Util.IndentationLetterFormat.ROMAN;

public class JsoupConverter {

    private HashMap<Integer, String> nextIndentationLetterMap;
    private int lastSeenIndentationLevel = 0;

    public JsoupConverter() {
        nextIndentationLetterMap = new HashMap<>();
        nextIndentationLetterMap.put(0, "1");
        nextIndentationLetterMap.put(1, "a");
        nextIndentationLetterMap.put(2, "1");
        nextIndentationLetterMap.put(3, "1");
        nextIndentationLetterMap.put(4, "a");
        nextIndentationLetterMap.put(5, "1");
        nextIndentationLetterMap.put(6, "1");
        nextIndentationLetterMap.put(7, "a");
        nextIndentationLetterMap.put(8, "1");
    }


    public String convertElementsToTextNodes(List<Node> nodes) {
        String fullText = "";
        for (Node node : nodes) {
            if (!(node instanceof Element)) {
                continue;
            }
            int indentationLevel = extractIndentationLevel(node);
            String indentationText;

            IndentationLetterFormat letterFormat = getTextFormat(indentationLevel);
            if (Objects.isNull(letterFormat)) {
                continue;
            }
            // TODO:
            //  1. indentation for roman is not reseted correctly
            //  2. everything is encoded, so \t and \n are cut off. Also < is encoded, so <br> is not possible
            //      to fix this: solution could be to just add "fake" tags(!t! for tab, !n! for newline) and replace them in the string later
            //  3. Test indentation again
            if (indentationLevel != lastSeenIndentationLevel && indentationLevel > 2) {
                resetLevelCounter(indentationLevel);
            }
            indentationText = generateIndentationText(letterFormat, indentationLevel);
            increaseLevelCounter(indentationLevel);
            lastSeenIndentationLevel = indentationLevel;
            fullText += indentationText + ((Element) node).text() + "\t";
            TextNode textNode = new TextNode(fullText);
            node.replaceWith(textNode);
        }
        return fullText;
    }

    /*
    todo: helperclass/parser
          ------------------
        - fuer roemische zahlen: RomanConverter
        - Irgendein helper, der automatisch richtiges Format waehlt (zahl, buchstabe oder römisch)
            * bei buchstaben wird a-z, aa-zz etc benoetigt
                -> generator generiert a-z, wenn z erreicht + 1 buchstabe von a bis z etc
        - Ebenen 1-9 als counter (hashmap? mit key css class)
        - last ebene seen variable
        - handle class method
            * wenn selbe Ebene: nummer/buchstabe in format rausgeben, counter + 1
            * wenn tiefere ebene:
                -> aktuelle ebene zwischen 1 und 2?: post number, counter + 1
                -> aktuelle ebene groeßer als 2: reset counter, post, counter + 1
            * wenn hoehere ebene:
                -> post und counter + 1
            * auf anzahl tabs achten
     */

    private int extractIndentationLevel(Node node) {
        String attributes = node.attr("class");
        if (attributes.isEmpty()) {
            return 0;
        }
        List<String> classNames = Arrays.asList(attributes.split(" "));
        List<String> indentClassList = classNames.stream().filter(cssClass -> cssClass.contains("ql-indent-")).collect(Collectors.toList());
        if (indentClassList.isEmpty()) {
            return 0;
        }
        String indentCssClass = indentClassList.get(0);
        return Integer.parseInt(Arrays.asList(indentCssClass.split("-")).get(2));
    }

    private IndentationLetterFormat getTextFormat(int indentationLevel) {
        IndentationLetterFormat letterFormat;
        switch (indentationLevel) {
            case 0:
            case 3:
            case 6:
                letterFormat = NUMBER;
                break;
            case 1:
            case 4:
            case 7:
                letterFormat = LETTER;
                break;
            case 2:
            case 5:
            case 8:
                letterFormat = ROMAN;
                break;
            default:
                letterFormat = null;
        }
        return letterFormat;
    }

    private void increaseLevelCounter(int indentationLevel) {
        String indentationLetter = nextIndentationLetterMap.get(indentationLevel);
        String nextIndentationLetter;
        if (indentationLetter.matches("[0-9]+")) {
            nextIndentationLetter = String.valueOf(Integer.parseInt(indentationLetter) + 1);
        } else {
            nextIndentationLetter = generateFollowupString(indentationLetter);
        }
        nextIndentationLetterMap.put(indentationLevel, nextIndentationLetter);
    }

    private void resetLevelCounter(int indentationLevel) {
        String indentationLetter = nextIndentationLetterMap.get(indentationLevel);
        String nextIndentationLetter = indentationLetter.matches("[0-9]+") ? "1" : "a";
        nextIndentationLetterMap.put(indentationLevel, nextIndentationLetter);
    }

    private String generateFollowupString(String previousString) {
        // TODO: make working for aa-zz etc and fix
        char lastLetter = previousString.charAt(previousString.length() - 1);
        return Character.toString(++lastLetter);
    }

    private String generateIndentationText(IndentationLetterFormat letterFormat, int indentationLevel) {
        String indentationText;
        String indentationLetter = nextIndentationLetterMap.get(indentationLevel);
        switch (letterFormat) {
            case ROMAN:
                indentationText = RomanConverter.getRomanNumber(Integer.valueOf(indentationLetter)) + ". ";
                break;
            case NUMBER:
            case LETTER:
                indentationText = indentationLetter + ". ";
                break;
            default:
                indentationText = "";
        }

        return indentationText;
    }
}
