package edu.depauw.scales.graphics

import Base._
import org.scalajs.dom

case class Bitmap(canvas: dom.HTMLCanvasElement, bounds: Bounds) extends Graphic {
  def render(ctx: GraphicsContext): Unit = ctx.drawImage(canvas, bounds.left, bounds.top, bounds.width, bounds.height)

  def toFunction: (Double, Double) => Color = {
    val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    val imagedata = ctx.getImageData(0, 0, canvas.width, canvas.height)

    (x: Double, y: Double) => {
      val col = (x * imagedata.width).toInt max 0 min (imagedata.width - 1)
      val row = (y * imagedata.height).toInt max 0 min (imagedata.height - 1)
      val index = (row * imagedata.width + col) * 4
      val r = imagedata.data(index)
      val g = imagedata.data(index + 1)
      val b = imagedata.data(index + 2)
      val a = imagedata.data(index + 3)
      RGBA(r, g, b, a)
    }
  }
}

object Bitmap {
  def apply(width: Double, height: Double)(fn: (Double, Double) => Color): Graphic = {
    val canvas = dom.document.createElement("canvas").asInstanceOf[dom.HTMLCanvasElement]
    val bounds: Bounds = RectBounds(0, width, 0, height)

    // TODO this is arbitrary...
    canvas.width = 500
    canvas.height = 500
    val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    val imagedata = ctx.createImageData(canvas.width, canvas.height)

    for (row <- 0 until imagedata.height; col <- 0 until imagedata.width) {
      val c = fn(col.toDouble / imagedata.width, row.toDouble / imagedata.height)
      val index = (row * imagedata.width + col) * 4
      imagedata.data(index) = c.red
      imagedata.data(index + 1) = c.green
      imagedata.data(index + 2) = c.blue
      imagedata.data(index + 3) = math.round(c.alpha * 255).toInt
    }
    ctx.putImageData(imagedata, 0, 0)

    Bitmap(canvas, bounds)
  }
}

object Image {
  // TODO this might not be useful, because of single-origin restrictions
  def apply(url: String, width: Int, height: Int): Graphic = {
    val canvas = dom.document.createElement("canvas").asInstanceOf[dom.HTMLCanvasElement]
    val bounds = RectBounds(0, width, 0, height)
    
    canvas.width = width
    canvas.height = height
    val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    
    val image = dom.document.createElement("img").asInstanceOf[dom.HTMLImageElement]
    image.addEventListener("load", (_: dom.Event) => ctx.drawImage(image, 0, 0, width, height))
    image.src = url
    
    Bitmap(canvas, bounds)
  }
}

object Freeze {
  def apply(graphic: Graphic): Graphic = {
    val canvas = dom.document.createElement("canvas").asInstanceOf[dom.HTMLCanvasElement]
    val bounds = graphic.bounds

    // Choose size for canvas so each dimension is at least 500, and the proportions are correct
    // TODO this is arbitrary...
    if (bounds.width < bounds.height) {
      canvas.width = 500
      canvas.height = (canvas.width * bounds.height / bounds.width).toInt
    } else {
      canvas.height = 500
      canvas.width = (canvas.height * bounds.width / bounds.height).toInt
    }
    
    graphic.displayOn(canvas)

    Bitmap(canvas, bounds)
  }
}