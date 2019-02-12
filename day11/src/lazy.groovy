
def str = new StringBuffer("hello")

def text = "say: ${str}"

println text

str.replace(0,5,"hhhhh")

println text
