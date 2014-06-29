package edu.depauw.scales.graphics

import Base._
  
trait Graphic {
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
  
  def t: Graphic = translate(0, -bounds.top)
  def m: Graphic = translate(0, -bounds.middle)
  def b: Graphic = translate(0, -bounds.bottom)
  def l: Graphic = translate(-bounds.left, 0)
  def c: Graphic = translate(-bounds.center, 0)
  def r: Graphic = translate(-bounds.right, 0)
  
  def fill(c: Color): Graphic = Styled(this, FillColor(c))
  def stroke(c: Color): Graphic = Styled(this, StrokeColor(c))
  def width(w: Double): Graphic = Styled(this, StrokeWidth(w)) // TODO better name?
}