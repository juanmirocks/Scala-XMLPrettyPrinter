package com.jmcejuela.scala.xml

import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import scala.xml.Atom
import scala.xml.Group
import scala.xml.NamespaceBinding
import scala.xml.Node
import scala.xml.SpecialNode
import scala.xml.Text
import scala.xml.dtd.DocType

/**
 * XML Pretty Printer.
 *
 * Advantages over scala.xml.PrettyPrinter:
 *
 *  1. You can both pretty-format a String or pretty-write directly to a file.
 *  2. pre-formatted elements: specify which nodes you want to be considered as pre-formatted.
 *    These are written exactly as they are read, with all the white spaces and new lines.
 *    All the other elements are completely stripped of leading & trailing whitespaces.
 *
 *    Consider for instance HTML-like documents (@see http://dev.w3.org/html5/html-xhtml-author-guide/)
 *    You can have the same behaviour as with the HTML <pre> tag.
 *    You could also have inlined <span>'s within a <p>
 *
 *  3. Thread safe: you can have the same (global) object used by different clients in parallel.
 *  4. Not tested, but presumably more efficient in both speed and space
 *  
 *  
 *
 * @author Juan Miguel Cejuela
 *
 *
 * @param indent: indentation space for a node's subnodes
 * @param pre: elements to be considered as pre-formatted
 */
class XMLPrettyPrinter(indent: Int, pre: String*) {
  require(indent >= 0, "the indent must be greater than or equal to zero")
  require(pre.forall(p => !(p == null || p.isEmpty())), "all pre elements must be non-empty")

  val preSet = pre.toSet

  /**
   * Pretty-format the node as a String.
   */
  def format(node: Node): String = {
    val buffer = SerializableBuffer()
    print(node)(buffer)
    buffer.toString
  }

  /**
   * Pretty-write the node to given file.
   *
   * The file is written with UTF-8 encoding and the file will include an xml declaration.
   * If you would like to change these defaults, contact the developer.
   */
  def write(node: Node, docType: DocType = null)(file: File) {
    val buffer = ClosableBuffer(file)
    buffer write "<?xml version=\"1.0\" encoding=\""+UTF8+"\"?>"
    buffer write ↵
    if (null != docType) {
      buffer write docType.toString
      buffer write ↵
    }

    print(node)(buffer)
    buffer.close()
  }

  /*---------------------------------------------------------------------------*/

  private val < = '<'
  private val > = '>'
  private val </ = "</"
  private val /> = "/>"
  private val ↵ = System.getProperty("line.separator");
  private val UTF8 = "UTF-8"

  val CONTENT: scala.util.matching.Regex = """(?s)\s*((?:\S.*\S)|\S|)\s*""".r

  def leavesText(node: Node) = {
    val sb = new StringBuilder
    def $(children: Seq[Node]): Option[String] = {
      if (children.isEmpty) Some(sb.toString)
      else {
        children.head match {
          case s: Text                                   => sb append s.toString; $(children.tail)
          case a: Atom[_] if a.data.isInstanceOf[String] => sb append a.toString; $(children.tail)
          case _                                         => None
        }
      }
    }
    $(node.child)
  }

  private def print(node: Node, pscope: NamespaceBinding = null, curIndent: Int = 0, inPre: Boolean = false)(implicit buffer: Buffer) {
    def whitespaceTrim(x: String) = x match { case CONTENT(c) => c }
    val preformatted = inPre || node.isInstanceOf[Group] || preSet.contains(node.label) //note, group.label fails
    def ::(x: String) = buffer write x
    def :::(x: Char) = buffer write x
    def __ = (0 until curIndent).foreach(_ => :::(' '))
    def printNodes(nodes: Seq[Node], newScope: NamespaceBinding, newIndent: Int) { nodes.foreach(n => print(n, newScope, newIndent, preformatted)) }

    node match {
      case _: SpecialNode =>
        if (preformatted) ::(node.toString)
        else ((x: String) => if (!x.isEmpty()) { __; ::(x); ::(↵) })((whitespaceTrim(node.toString)))
      case Group(group) => printNodes(group, pscope, curIndent)
      case _ =>
        if (!inPre) __
        ::(startTag(node, pscope)); {
          if (preformatted) {
            printNodes(node.child, node.scope, curIndent + indent)
          } else {
            val leaves = leavesText(node).map(s => Some(whitespaceTrim(s))).getOrElse(None)
            if (leaves.isDefined) {
              ::(leaves.get)
            } else {
              ::(↵)
              printNodes(node.child, node.scope, curIndent + indent)
              __
            }
          }
        }
        ::(endTag(node))
        if (!inPre) ::(↵)
    }
  }

  /*---------------------------------------------------------------------------*/

  //These functions were copied outright from scala.xml.{Utility, PrettyPrinter}

  private def sbToString(f: (StringBuilder) => Unit): String = {
    val sb = new StringBuilder
    f(sb)
    sb.toString
  }

  private def startTag(n: Node, pScope: NamespaceBinding): String = {
    def mkStart(sb: StringBuilder) {
      sb append <
      n nameToString sb
      n.attributes buildString sb
      n.scope.buildString(sb, pScope)
      sb append >
    }
    sbToString(mkStart)
  }

  private def endTag(n: Node)(implicit buffer: Buffer): String = {
    def mkEnd(sb: StringBuilder) {
      sb append </
      n nameToString sb
      sb append >
    }
    sbToString(mkEnd)
  }

  /*---------------------------------------------------------------------------*/

  private trait Buffer {
    def write(x: String): Unit
    def write(x: Char): Unit
  }
  private trait ClosableBuffer extends Buffer {
    def close(): Unit
  }
  private object ClosableBuffer {
    def apply(file: File) = new ClosableBuffer {
      val bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), UTF8))
      def write(x: String) { bw.write(x) }
      def write(x: Char) { bw.write(x) }
      def close() { bw.close() }
    }
  }

  private trait SerializableBuffer extends Buffer {
    override def toString(): String
  }
  private object SerializableBuffer {
    def apply() = new SerializableBuffer {
      val sb = new StringBuilder()
      def write(x: String) { sb.append(x) }
      def write(x: Char) { sb.append(x) }
      override def toString() = sb.toString
    }
  }
}