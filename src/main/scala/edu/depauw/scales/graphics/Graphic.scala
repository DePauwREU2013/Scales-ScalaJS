package edu.depauw.scales.graphics

import Base._
import org.scalajs.dom
import edu.depauw.scales.music._
import edu.depauw.scales.reactive._
import edu.depauw.scales.act._

trait Graphic extends Scales {

  //added 
  def act(time: Double = 0): Unit = {
    Anim(Reactive.ClockTickChanges(1,1), (x: Double) => { this } ).act(time)
  } 

  //added
  def duration: Double = 0

  //added
  def transformAct(s: Double): Graphic = scale(s)

  def bounds: Bounds

  def render(ctx: GraphicsContext): Unit

  def transform(xform: AffineTransform): Graphic = Transformed(this, xform)

  def translate(dx: Double, dy: Double): Graphic =
    transform(AffineTransform.getTranslateInstance(dx, dy))

  def rotate(ang: Angle): Graphic =
    transform(AffineTransform.getRotateInstance(ang))

  def scale(s: Double): Graphic =
    transform(AffineTransform.getScaleInstance(s, s))

  def scale(sx: Double, sy: Double): Graphic =
    transform(AffineTransform.getScaleInstance(sx, sy))

  // TODO skew; reflect; rotate about a point; scale about a point; ...?

  def on(that: Graphic): Graphic = Composite(this, that)

  def beside(that: Graphic): Graphic = {
    val dx = bounds.right - that.bounds.left
    on(that.translate(dx, 0))
  }

  def above(that: Graphic): Graphic = {
    val dy = bounds.bottom - that.bounds.top
    on(that.translate(0, dy))
  }
  
  def tl: Graphic = translate(-bounds.left, -bounds.top)
  def top: Graphic = translate(-bounds.center, -bounds.top)
  def tr: Graphic = translate(-bounds.right, -bounds.top)
  def left: Graphic = translate(-bounds.left, -bounds.middle)
  def center: Graphic = translate(-bounds.center, -bounds.middle)
  def right: Graphic = translate(-bounds.right, -bounds.middle)
  def bl: Graphic = translate(-bounds.left, -bounds.bottom)
  def bottom: Graphic = translate(-bounds.center, -bounds.bottom)
  def br: Graphic = translate(-bounds.right, -bounds.bottom)

  def fill(c: Color): Graphic = Styled(this, FillColor(c))
  def stroke(c: Color): Graphic = Styled(this, StrokeColor(c))
  def strokeWidth(w: Double): Graphic = Styled(this, StrokeWidth(w))
  
  def freeze: Graphic = Freeze(this)
  
  def pad(fraction: Double): Graphic = {
    val extraWidth = bounds.width * fraction / 2
    val extraHeight = bounds.height * fraction / 2
    val newBounds = RectBounds(bounds.left - extraWidth, bounds.right + extraWidth,
        bounds.top - extraHeight, bounds.bottom + extraHeight)
    Bounded(this, newBounds)
  }
  
  def showBounds: Graphic = {
    val box = Rectangle(bounds.width, bounds.height).tl.translate(bounds.left, bounds.top)
    val axes = Shape(bounds.width, bounds.height) {
      (-bounds.left / bounds.width, -0.1) lineTo (-bounds.left / bounds.width, 1.1) moveTo
      (-0.1, -bounds.top / bounds.height) lineTo (1.1, -bounds.top / bounds.height)
    }.tl.translate(bounds.left, bounds.top)
    (box on axes).fill(Color.Clear).stroke(Color.Black).strokeWidth(1) on this
  }
  
  def displayOn(canvas: dom.HTMLCanvasElement): Unit = {
    val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    val scaleFactor = (canvas.width / bounds.width) min (canvas.height / bounds.height)
    this.tl.scale(scaleFactor).render(ctx)
  }
}
// TODO add other bounds manipulation