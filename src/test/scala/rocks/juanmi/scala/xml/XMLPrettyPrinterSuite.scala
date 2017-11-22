package com.jmcejuela.scala.xml

import org.scalatest.FunSuite
import scala.xml.Node
import java.io.File
import scala.xml.XML
import java.io.InputStreamReader
import java.io.FileInputStream
import scala.xml.PrettyPrinter
import scala.xml.Group
import scala.xml.Text
import scala.xml.XML
import scala.xml.transform.RewriteRule
import scala.xml.transform.RuleTransformer
import javax.xml.parsers.SAXParserFactory
import org.xml.sax.InputSource
import com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl

class XMLPrettyPrinterSuite extends FunSuite {
  val printer = new XMLPrettyPrinter(2)
  val printerWithPres = new XMLPrettyPrinter(2, "a", "pre", "code", "p", "h1", "h2", "h3", "h4", "h5", "h6")

  val testResources = List(
    "journal.pgen.1002681.xml",
    "w3schools_example.xml",
    "w3schools_example_badlyformatted.xml")

  /*--------------------------------------------------------------------------*/

  test("Minimized/void elements remain minimized") {
    assert("<p/>\n" === printer.format(<p/>))
    assert("<p class=\"something\"/>\n" === printer.format(<p class="something"/>))
    assert("<p class=\"something\"/>\n" === printerWithPres.format(<p class="something"/>))
  }

    /*
     * === DO NOT AUTO-FORMAT THIS REGION OF THE FILE ===
     */

  test("XML pretty pre-formatting  :::  This is a NON-FORMAL, VISUAL-ONLY test !") {
    val n1 = 
      <notPre>
                             <!-- pre-formatting pretty-printing test (this comment should be aligned)-->
            <pre>    
 
            
                 ..... . . . . 
.</pre></notPre>
      
    val n2 = 
      <notPre>


<!-- pre-formatting pretty-printing test (this comment should be aligned)-->

<pre>         ....      </pre>

<notPre><span>  child span </span>
                     
                     <!-- another comment -->
      <pre>   !!!  an <span>inlined span</span> doesn't get broken and can have<span> spaces!!   </span><span>:)</span></pre>
   

                      <span>    child span with specials: > {"&"} ; {"<pio>*</pio> <!-- ? -->"}</span>
</notPre>

</notPre>



    println(printerWithPres.format(n1))
    println(printerWithPres.format(n2))

  }

  /*--------------------------------------------------------------------------*/

  test("XML content remains the same after pretty-formatting") {
    testResources.foreach(resource => {
      val file = resourceFile(resource)
      val node = XMLloadUTF8(file)
      val normalizedNode = normalize(node)

      /* The formatted strings must be converted back to node for logical comparison.
       * node.toString and printer.format may yield for nodes without children either
       * <tag/> or <tag></tag>, xml-equivalent but different strings */
      assert(normalizedNode === normalize(XML.loadString(printer.format(node))))
      assert(normalizedNode === normalize(XML.loadString(printerWithPres.format(node))))
    })
  }

  test("Handle Group[Node]") {
    val group: Node = Group(Seq(<!-- Group[Node] test -->, <span>  ...   </span>, Text("buh!"), <span> [---] </span>, Text(" .... ")))

    println(printer.format(group))

  }

  test("Pretty-writing & load with DOCTYPE") {
    val resource = "journal.pgen.1002681.xml"
    val docType = xml.dtd.DocType("article", xml.dtd.PublicID("-//NLM//DTD Journal Publishing DTD v2.0 20040830//EN", "http://dtd.nlm.nih.gov/publishing/2.0/journalpublishing.dtd"), Nil)

    val file = resourceFile(resource)
    val node = XMLloadUTF8(file)

    //Can't trivially test that the contents are the same because XML.load does not guarantee the order of the attributes

    withTmpFile(resource) { f =>
      printer.write(node, docType)(f)
      val prettyNode = XMLloadUTF8(f)
    }

    withTmpFile("pres_"+resource) { f =>
      printerWithPres.write(node, docType)(f)
      val prettyNode = XMLloadUTF8(f)
    }
  }

  /*--------------------------------------------------------------------------*/

  //Remove empty text nodes and trim all other text nodes
  object normalize extends RuleTransformer(new RewriteRule() {
    override def transform(n: Node): Seq[Node] = {
      n.filter(e => e match {
        case Text(text) if text.matches("\\s*") => false
        case _ => true
      }).map(e => e match {
        case Text(text) => Text(text.trim)
        case _ => e
      })
    }
  })

  class NonValidatingParserFactory extends SAXParserFactoryImpl() {
    setFeature("http://xml.org/sax/features/validation", false);
    setFeature("http://apache.org/xml/features/disallow-doctype-decl", false);
    setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
    setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
  }

  def XMLloadUTF8(file: File, validateSchema: Boolean = false) = {
    val parser =
      if (validateSchema) SAXParserFactory.newInstance.newSAXParser()
      else new NonValidatingParserFactory().newSAXParser()

    XML.loadXML(new InputSource(new InputStreamReader(new FileInputStream(file), "UTF-8")), parser)
  }

  def resourceFile(resource: String) = new File(classOf[XMLPrettyPrinterSuite].getResource("/"+resource).toURI)

  def withTmpFile(name: String)(body: File => Unit) {
    val tmpFile = java.io.File.createTempFile(classOf[XMLPrettyPrinter].getSimpleName()+"_test_"+name, ".xml")
    body(tmpFile)
    println(tmpFile.getAbsolutePath)
  }
}
