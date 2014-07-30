package edu.depauw.scales.demo

import scala.scalajs.js
import org.scalajs.dom
import rx._

import edu.depauw.scales.graphics._
import edu.depauw.scales.reactive._

trait Slide {
  def font: String = "serif"
  def fontSize: Int = 20
  def color: Base.Color = Base.Color.Black
  def indicator: Int = 0
}

case class TitleStart(val contents: String) extends Slide {
  override def fontSize = 48
  override def color = Base.Color.SeaGreen
  override def indicator = 1
}

case class Title(val contents: String) extends Slide {
  override def fontSize = 48
  override def color = Base.Color.SeaGreen
}

case class SubtitleStart(val contents: String) extends Slide {
  override def fontSize = 30
  override def color = Base.Color.SeaGreen
  override def indicator = 1
}

case class Subtitle(val contents: String) extends Slide {
  override def fontSize = 30
  override def color = Base.Color.HotPink
}

case class BulletStart(val contents: String) extends Slide {
  override def indicator = 1
}

case class Bullet(val contents: String) extends Slide {}

case class RegularStart(val contents: String) extends Slide {
  override def indicator = 1
}

case class Regular(val contents: String) extends Slide {}

case class Custom(val contents: String, val f: String, val fs: Int, val c: Base.Color, val i: Int = 0) extends Slide {
  override def font = f
  override def fontSize = fs
  override def color = c
  override def indicator = i 
}

case class ImageStart(val contents: Graphic) extends Slide {
  override val indicator = 1
}

case class Image(val contents: Graphic) extends Slide {}

case class ImageLeftStart(val contents: Graphic) extends Slide {
  override val indicator = 1
}

case class ImageLeft(val contents: Graphic) extends Slide {}




