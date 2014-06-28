package edu.depauw.scales.graphics

import Base._

case class Styled(graphic: Graphic, style: Style) extends Graphic {
  def bounds: Bounds = graphic.bounds
  
  def render(ctx: GraphicsContext): Unit = {
    ctx.save()
    style(ctx)
    graphic.render(ctx)
    ctx.restore()
  }
}