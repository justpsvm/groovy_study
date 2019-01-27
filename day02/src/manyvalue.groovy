
def category(catagorys){
	catagorys.split("/")
}

//def (category1,category2) = category("apple/banana")

//println "$category1,$category2"

def (category1,category2,category3) = category("apple/banana")

println "$category1,$category2,$category3"


def v1 = 1
def v2 = 2

(v1,v2) = [v2,v1]

println "$v1,$v2"




