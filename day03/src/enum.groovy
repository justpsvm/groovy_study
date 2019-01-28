
enum TankMoveEvent {

	上('top'),下('bottom'),左('left'),右('right')

	def code

	TankMoveEvent(code){
		this.code = code
	}

}

// def printDirection(direction){
// 	println "坦克正在向 $direction 移动"
// }

def printDirection(direction){
	println "坦克正在向 $direction.code 移动"
}

printDirection(TankMoveEvent.上)

def count = TankMoveEvent.values().length

println "坦克一共有 $count 个可移动的方向，分别是:"

for(direction in TankMoveEvent.values()){
	println direction.code;
}







