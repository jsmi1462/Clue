import math
sum = 1
digSum = 0
for i in range (1,1001):
    sum = sum * 2
    print("sum:", sum)
    print("i is ", i)

print(sum)

otherSum = 1
for j in range (1,500):
    sum = sum * 4
    print("sum:", otherSum)
    print("i is ", j)

print(sum)




while sum > 0:
    digSum += sum%10
    sum = sum/10

print("The sum of the digits is:", digSum)