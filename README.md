# XMLPrettyPrinter

Advantages over scala.xml.PrettyPrinter:
  1. You can both pretty-format a String or pretty-write directly to a file.
  2. pre-formatted elements: specify which nodes you want to be considered as pre-formatted.
    These are written exactly as they are read, with all the white spaces and new lines.
    All the other elements are completely stripped of leading & trailing whitespaces.

    Consider for instance [HTML-compatible XHTML documents](http://dev.w3.org/html5/html-xhtml-author-guide/)
    You can have the same behavior as with the HTML &lt;pre> tag.
    You could also have inlined &lt;span>'s within a &lt;p> without creating spurious break lines.

  3. Thread safe: you can have the same (global) object used by different clients in parallel.
  4. Not tested, but presumably more efficient in both speed and space


## Instructions

Simply copy the [XMLPrettyPrinter](https://github.com/jmcejuela/Scala-XML-Pretty-Printer/blob/master/src/main/scala/com/jmcejuela/scala/xml/XMLPrettyPrinter.scala) file to your project's code