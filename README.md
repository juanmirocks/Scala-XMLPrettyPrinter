# XMLPrettyPrinter ✨ _scala support for: 2.11, 2.12, 2.13_

Advantages over `scala.xml.PrettyPrinter`:

1. You can both pretty-format a String or **pretty-write directly to a java File, OutputStream, or Writer**.
2. **Pre-formatted elements**: specify which nodes you want to be considered as pre-formatted.
   These are written exactly as they are read, with all the white spaces and new lines.
   All the other elements are completely stripped of leading & trailing whitespaces.

Consider for instance [Polyglot Markup documents](http://dev.w3.org/html5/html-polyglot/html-polyglot.html).
You can write in XML with the same behavior as with the HTML `<pre>` tag.
You could also have inlined `<span>`'s within a `<p>` without creating spurious break lines.

3. **Thread safe**: you can have the same printer object used by different clients in parallel.

4. **Faster and consumes less memory** when you write directly to your desired output, thus avoiding an in-between conversion to String.

## Output Example

```xml
<notPre>
  <!-- pre-formatting pretty-printing test (this comment should be aligned)-->
  <pre>         ....      </pre>
  <notPre>
    <span>child span</span>
    <!-- another comment -->
    <pre>   !!!  an <span>inlined span</span> doesn't get broken and can have<span> spaces!!   </span><span>🙂</span></pre>
    <span>child span with specials: &gt; &amp; ; &lt;pio&gt;*&lt;/pio&gt; &lt;!-- ? --&gt;</span>
  </notPre>
</notPre>
```


## How to use

`XMLPrettyPrinter` is self-contained and has no dependencies other than the standard [scala-xml](https://github.com/scala/scala-xml) library.

1. Make sure you have `scala-xml` available in your project.

```scala
libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.2.0"
```

2. Drop the [single-file code](src/main/scala/rocks/juanmi/scala/xml/XMLPrettyPrinter.scala) into your project
