fun main(args: Array<String>) {
    print("Hello World!")
}

fun sum(a: Int, b: Int): Int {
    return a + b
}

fun sum(a: Int, b: Int) = a + b


fun max(a: Int, b: Int) if (a > b) a else b

val a: Int = 1

val b = 2

val c = 3.14

val d: String

d = "필수로 있어야 하는 구문"
// d = "d의 초기값이 없으면 null이 될 수 있는데, d는 null이 될 수 없기 때문에."

val e: String?

var d: String = "첫번째 초기화"
e = "두번째 초기화"

// == for ==

for (i in 1..5) {
    print(i)
}
// 1 2 3 4 5

for (i in 6 downTo 0 step 2) {
    print(i)
}
// 6 4 2 0

for (i in 5 step 3) {
    print(i)
}
// 1 4

val nunberList = listof(100, 200, 300)
for (number in numberList) {
    print(number)
}
// 100 200 300

// == while ==

var x = 5
while (x > 0) {
    print(x)
    x--
}
// 5 4 3 2 1

x = 0
while (x > 0) {
    print(x)
    x--
}
//출력 없음

var y = 0
do {
    print(y)
    y--
} while (y > 0)
// 0

// == if ==

var max: Int
if (a > b) {
    max = a
} else {
    max = b
}

// As experssion
val max = if (a > b) {
    print("Choose a")
    a
} else {
    print("Choose b")
    b
}

// == when ==

when(x){
    1 -> print("x == 1")
    2 -> print("x == 2")
    else -> {
        println("x is neither 1 nor 2")
    }
}

when(x){
    0,1 -> print("x == 0 or x == 1")
    else -> print("otherwise")
}

when(x){
    in 1..10 -> print("x는 1부터 10 범위 안에 있음")
    !in 10..20 -> print("x는 10부터 20 범위 안에 없음")
    else -> print("otherwise")
}

when(x){
    is Int -> print("x는 Int형")
    else -> print("x는 인트형이 아님")
}