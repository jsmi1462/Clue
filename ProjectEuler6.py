import math
sumOfSquares = 0
squareOfSums = 0
for i in range (1,101): #numbers 1 through 100
    sumOfSquares += (i*i)
    #print(sumOfSquares)
    squareOfSums += i
    print(squareOfSums)
squareOfSums = squareOfSums * squareOfSums
print("squareOfSums", squareOfSums)
print(squareOfSums - sumOfSquares)

print(math.pow(2,1000))
