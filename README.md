# XMLPrettyPrinter v0.1

Simple XML Pretty Printer for Scala Node

Advantages over scala.xml.PrettyPrinter:
  1. You can both pretty-format a String or pretty-write directly to a file.
  2. pre-formatted elements: specify which nodes you want to be considered as pre-formatted.
    These are written exactly as they are read, with all the white spaces and new lines.
    All the other elements are completely stripped of leading & trailing whitespaces.

    Consider for instance [HTML-compatible XHTML documents](http://dev.w3.org/html5/html-xhtml-author-guide/)
    You can have the same behaviour as with the HTML <pre> tag.
    You could also have inlined <span>'s within a <p>

  3. Thread safe: you can have the same (global) object used by different clients in parallel.
  4. Not tested, but presumably more efficient in both speed and space


## Instructions

Simply copy the XMLPrettyPrinter file to your project's code