map ="""xxxxxxx xxxxxxxx xxxxxxx
xxxxxxx  xxxxxx  xxxxxxx
xxxxxxx  xxxxxx  xxxxxxx
xxxxxxx  xxxxxx  xxxxxxx
x        dxxxxx  xxxxxxx
         xxxxxx  xxxxxxx
xxxxxx   xxddxx        x
xxxxxxx
xxxxxxd  xxxxx         x
xxxxxxx  xxxxx  xxxxxxxx
xxxxxx   xxxxx  xxxxxxxx
x        xxxxx  xxxxxxxx
xxxxxx   xxxxx  xxxxxxxx
xxxxxx   xxxxx  xxxxxxxx
xxxxxx   xxxxx  xxxxxxxx
xxxxxx             xxxxx
xxxxxx                 x
x        xxxxxxxx
         xxxxxxxx xxxxxx
xxxxx    xxxxxxxx xxxxxx
xxxxxx   xxxxxxxx xxxxxx
xxxxxx   xxxxxxxx xxxxxx
xxxxxx   xxxxxxxx xxxxxx
xxxxxxx   xxxx   xxxxxxx
xxxxxxxxx xxxx xxxxxxxxx"""


for i, row in enumerate(map.split("\n")):
    print(f'this.addrow("{row}", {i});')
