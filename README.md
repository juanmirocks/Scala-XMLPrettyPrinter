# XMLPrettyPrinter

Advantages over `scala.xml.PrettyPrinter`:

1. You can both pretty-format a String or pretty-write directly to a file.
2. pre-formatted elements: specify which nodes you want to be considered as pre-formatted.
   These are written exactly as they are read, with all the white spaces and new lines.
   All the other elements are completely stripped of leading & trailing whitespaces.

Consider for instance [Polyglot Markup documents](http://dev.w3.org/html5/html-polyglot/html-polyglot.html).
You can write in XML with the same behavior as with the HTML `<pre>` tag.
You could also have inlined `<span>`'s within a `<p>` without creating spurious break lines.

3. Thread safe: you can have the same (global) object used by different clients in parallel.
4. Not tested, but presumably more efficient in both speed and space


## How to use

XMLPrettyPrinter is self-contained and has no dependencies.

Copy the [code](https://github.com/jmcejuela/Scala-XML-Pretty-Printer/blob/master/src/main/scala/com/jmcejuela/scala/xml/XMLPrettyPrinter.scala) and drop into your project.
