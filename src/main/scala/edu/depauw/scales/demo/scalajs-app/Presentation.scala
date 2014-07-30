package edu.depauw.scales.demo

import scala.scalajs.js
import org.scalajs.dom
import rx._

import edu.depauw.scales.graphics._

object Presentation {

  import Base._

  val title1 = "Title of Slide #1"
  val title2 = "Title of Slide #2"
  val title3 = "Title of Slide #3"
  val title4 = "Title of Slide #4"
  
  val subtitle1 = "Subtitle #1: By Anonymous x 3"
  val subtitle2 = "Subtitle #2"
  val subtitle3 = "Subtitle #3"
  val subtitle4 = "Subtitle #4"

  val lorem1 = "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " + 
    "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "+
    "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
    "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."

  val lorem2 = "Curabitur sodales ligula in libero. Sed dignissim lacinia nunc. Curabitur tortor. " +
    "Pellentesque nibh. Aenean quam. In scelerisque sem at dolor. Maecenas mattis. Sed convallis tristique sem." + 
    " Proin ut ligula vel nunc egestas porttitor. Morbi lectus risus, iaculis vel, suscipit quis, luctus non, massa. " +
    "Fusce ac turpis quis ligula lacinia aliquet. Mauris ipsum."   

  val lorem3 = "Maecenas placerat velit sit amet libero scelerisque, ut luctus odio consectetur."

  val lorem4 = "Praesent tempor ante vitae nisi viverra, vel rutrum sem molestie."

  val lorem5 = "Integer id ante malesuada, consectetur dolor et, rutrum est.Maecenas at odio luctus, commodo felis eu, molestie lectus."

  val lorem6 = "Nulla tincidunt justo eget enim lacinia aliquet. " + 
    "Quisque eu nulla et mauris eleifend ullamcorper. Integer bibendum sapien interdum quam blandit, sed eleifend libero scelerisque. " +
    "Vestibulum pretium nibh quis diam auctor, eget tincidunt ipsum imperdiet. Cras semper quam et varius cursus."

  val lorem7 = "Donec at justo consequat, dictum nibh quis, fringilla turpis."

  val lorem8 = "Lorem ipsum"

  val lorem9 = "Fusce a facilisis augue. Duis egestas odio a vulputate consectetur. " + 
    "Morbi sagittis molestie urna et vehicula. Nam ornare mauris quis risus faucibus, ut pharetra tortor hendrerit. " + 
    "Praesent eu sollicitudin ante. Vestibulum nec varius sem. Cras mi nibh, interdum non lobortis eu, blandit euismod tellus. " 

  val graphic1 = Rectangle(100, 100).fill(Color.RoyalBlue)

  val graphic2 = Rectangle(100, 100).fill(Color.Yellow) beside Ellipse(100, 100).fill(Color.HotPink)

  val lorem10 = "Le Lorem Ipsum est simplement du faux texte employé dans la composition et la mise en page avant impression. " + 
    "Le Lorem Ipsum est le faux texte standard de l'imprimerie depuis les années 1500, quand un peintre anonyme assembla ensemble des " + 
    "morceaux de texte pour réaliser un livre spécimen de polices de texte."

  val graphic3 = Image("https://tse4.mm.bing.net/th?id=HN.608021731299754631&pid=1.7", 400, 300)

  val text: List[Slide] = List(
    TitleStart(title1),
    Subtitle(subtitle1),
    Bullet(lorem1),
    Bullet(lorem2),
    TitleStart(title2),
    Subtitle(subtitle2),
    Bullet(lorem3),
    Bullet(lorem4),
    Bullet(lorem5),
    TitleStart(title3),
    Subtitle(subtitle3),
    Bullet(lorem6),
    Bullet(lorem7),
    Bullet(lorem8),
    TitleStart(title4),
    Subtitle(subtitle4),
    Regular(lorem9),
    Img(graphic1),
    ImgLeft(graphic2),
    Custom(lorem10, "monospace", 12, Color.RoyalBlue),
    ImgStart(graphic3)
  )
}