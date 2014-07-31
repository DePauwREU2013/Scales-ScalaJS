val canvas = Canvas.canvas
val width = canvas.width
val height = canvas.height

val numKeys = 16
val kh = height / 2
val kw = canvas.width / numKeys

val bkh = kh / 2
val bkw = kw / 2

//keys
val whiteKey = Rectangle(kw, kh).strokeWidth(5).stroke(Color.Black).fill(Color.White).tl
val blackKey = Rectangle(bkw, bkh).strokeWidth(5).stroke(Color.Black).fill(Color.Black).tl

val twoBlackKeys = blackKey.translate(3 * kw / 4, 0) beside blackKey.tl.pad(2)
val threeBlackKeys =  blackKey beside blackKey.tl.pad(2) beside blackKey
val fiveBlackKeys = twoBlackKeys.pad(1) beside threeBlackKeys


val clearKey = Rectangle(2 * kw - (kw / 2), kh / 2).stroke(Color.Clear).fill(Color.Clear).tl
val clearKey2 = Rectangle(kw, kh / 2).stroke(Color.Clear).fill(Color.Clear).tl
val stwobk = clearKey beside twoBlackKeys
val sthreebk = clearKey2 beside threeBlackKeys
val singleBlackKey = clearKey beside blackKey

def generateWhiteKeys(i: Int, num: Int): Graphic = ((i+1) == num) match {
    case true => whiteKey
    case false => whiteKey beside generateWhiteKeys(i + 1, num)
}

//wip
def fnGetNote(xy: (Int, Int)): ScalesNote = {
    val x = xy._1
    val y = xy._2
    
    if(y < kh) {
        lazy val bk1 = (bkw + bkw / 2)
        lazy val bk2 = (bk1 + kw)
        
        //first white key
        if(x < bk1 || (x < kw && y > bkh)) Note(444, .5)
        
        //first black key
        else if(y < bkh && (x > bk1 && x < bk1 + bkw)) Note(1000, .5)
        
        //second white key
        else if(x < bk2 || (x < 2 * kw && y > bkh)) Note(500, .5)
        
        
        // if(x < kw) {
        //   if(y > bkh && x > bkw) {
               
        //   }
        //     return Note(444, .2)
        // } else if(x < kw * 2) {
        //     return Note(1000, .2)
        // } else if(x < kw * 3) {
        //     return Note(300, .2, 2)
        // }
        else Silent
    }
    else Silent
}

//rendering
lazy val ks = (fiveBlackKeys beside stwobk beside sthreebk beside singleBlackKey) on generateWhiteKeys(0, numKeys)

//ks.render(Canvas.ctx)

val playing = Anim(width, height)(Reactive.MouseClick, fnGetNote)//.act()

(Act(ks) on Act(playing)).act()