package edu.depauw.scales.graphics

import Base._

case class Transformed(graphic: Graphic, xform: AffineTransform) extends Graphic {
  lazy val bounds: Bounds = graphic.bounds.transform(xform)
  
  def render(ctx: GraphicsContext): Unit = {
    ctx.save()
    ctx.transform(xform.m11, xform.m12, xform.m21, xform.m22, xform.dx, xform.dy)
    graphic.render(ctx)
    ctx.restore()
  }
  
  override def transform(xform2: AffineTransform): Graphic =
    Transformed(graphic, xform2 * xform)
}