package edu.depauw.scales.graphics

import Base._
import org.scalajs.dom

case class Text(text: String, font: Font = Font.Sans10, outline: Boolean = false) extends Graphic {
  def bounds: Bounds = {
    RectBounds(0, font.measure(text), -font.size, 0) // bottom of the box is at the baseline; text will probably descend below...
  }

  def render(ctx: GraphicsContext): Unit = {
    ctx.save()
    ctx.font = font.toString
    if (outline) {
      ctx.strokeText(text, 0, 0)
    } else {
      ctx.fillText(text, 0, 0)
    }
    ctx.restore()
  }
}

case class Font(family: String, size: Int, italic: Boolean = false, smallCaps: Boolean = false, bold: Boolean = false) {
  override def toString: String = {
    var result = size + "px " + family
    if (bold) result = "bold " + result
    if (smallCaps) result = "small-caps " + result
    if (italic) result = "italic " + result
    result
  }

  def measure(text: String): Double = {
    val ctx = Font.metricCanvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    ctx.font = toString
    ctx.measureText(text).width
  }
}

object Font {
  private val metricCanvas = dom.document.createElement("canvas").asInstanceOf[dom.HTMLCanvasElement]
  val Sans10 = Font("sans-serif", 10)
  val Serif10 = Font("serif", 10)
  val Mono10 = Font("monospace", 10)
  // TODO etc.
}