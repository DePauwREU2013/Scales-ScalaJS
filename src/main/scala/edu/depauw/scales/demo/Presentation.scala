package edu.depauw.scales.demo

import scala.scalajs.js
import org.scalajs.dom
import rx._

import edu.depauw.scales.graphics._

object Presentation {

  val title1 = "Presentation: SCALES IDE"
  
  val subtitle1 = "By Anonymous x 3"

	val loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " + 
    "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "+
    "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
    "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."

  val lorem2 = "Curabitur sodales ligula in libero. Sed dignissim lacinia nunc. Curabitur tortor. " +
    "Pellentesque nibh. Aenean quam. In scelerisque sem at dolor. Maecenas mattis. Sed convallis tristique sem." + 
    " Proin ut ligula vel nunc egestas porttitor. Morbi lectus risus, iaculis vel, suscipit quis, luctus non, massa. " +
    "Fusce ac turpis quis ligula lacinia aliquet. Mauris ipsum."   


	val text = List(
		(1, Title, title1),
    (0, Subtitle, subtitle1),
    (0, RegularBullet, loremIpsum),
    (0, RegularBullet, lorem2),
    (1, Title, subtitle1),
    (0, Subtitle, title1),
    (1, RegularBullet, lorem2),
    (0, RegularBullet, loremIpsum)
	)
}