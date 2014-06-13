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
    
  // TODO rotate about a point; scale about a point; ...
    
  def atop(that: Graphic): Graphic = Composite(this, that)
  
  def beside(that: Graphic): Graphic = {
    val dx = bounds.right - that.bounds.left
    atop(that.translate(dx, 0))
  }
    
  def above(that: Graphic): Graphic = {
    val dy = bounds.bottom - that.bounds.top
    atop(that.translate(0, dy))
  }
}