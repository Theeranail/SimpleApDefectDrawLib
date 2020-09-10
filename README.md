# Drawing Library
##### Last version : Master

[SimpleApDefectDrawLib](https://github.com/Theeranail/SimpleApDefectDrawLib "SimpleApDefectDrawLib")  เป็น library ที่ใช้ในการวาดรูปสำหรับ AP Defect เพื่อให้ง่ายต่อการเรียกใช้งาน

**วิธีการใช้งาน**
##### Gradle (Project)
    allprojects {
        repositories {
           //.......
            maven { url 'https://jitpack.io' }
        }
    }
##### Gradle Dependencies
    implementation com.github.Theeranail:SimpleApDefectDrawLib:{version}
##### Main Layout .xml
```xml
 <com.example.apdefectdrawlibrary.APDefectDraw
        android:id="@+id/defectDraw"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintBottom_toTopOf="parent">
    </com.example.apdefectdrawlibrary.APDefectDraw>
```
##### MainActivity
เรียกใช้งาน จะเรียกใช้งานได้สองแบบดังนี้
**1. เรียกใช้แบบปกติ**
```java
defectDraw.imagePath ="https://www.banidea.com/wp-content/uploads/2012/04/lower-floor-plan.jpg"
defectDraw.onDefectDrawListener = this
defectDraw.initViewImage();
```
**2. เรียกใช้งานผ่าน Builder Class ดูเป็นระบียบมากกว่าขึ้นอยู่ว่าชอบแบบใหน**
```java
var lateinit var apDefectDraw: APDefectDraw

apDefectDraw = DefectDrawBuilder.instances()
	.imagePath("https://www.banidea.com/wp-content/uploads/2012/04/lower-floor-plan.jpg")
	.defectDrawListener(this)
	.build(defectDraw)
```
#### Event เมื่อกด save จะส่งค่า bitmap กลับมาให้
โดยการ implement Class **OnDefectDrawListener** ใน MainActibity กลังจากนั้นก็ override method onSaveDraw
```java
//ตัวอย่าง    
override fun onSaveDraw(image: Bitmap?) {
        if (image != null) {
            Toast.makeText(this, "export image to Bitmap success", Toast.LENGTH_SHORT).show()
            apDefectDraw.reset()
        } else {
            Toast.makeText(this, "export image to Bitmap Invalid", Toast.LENGTH_SHORT).show()
        }
    }
```
- reset() : คือ ล้างค่าทั้งหมดของการวาด
- imagePath:String = url รูปภาพที่ต้องการวาดทัพ
- strokeWidth:Float = ขนาดเส้นการวาด default 2.0
- onDefectDrawListener:Interface = event สำหรับการ save
- initViewImage() = สั่งให้แสดงรูปภาพที่ต้องการวาดทับ (ถ้าค้องการให้เรียกใช้คำสั่งนี้ทุกครั้ง)
- isShowButtonSave(isShowButtonSave:Boolean) = ต้องการให้แสดงปุ่ม save หรือ ไม่
- exportBitmap() = export รูปภาพออกเป็น bitmap function เดียวกันกับปุ่ม save
- isShowTools(isShow: Boolean) = สั่งให้แสดง tools หรือ ไม่แสดง true -> แสดง false -> ไม่แสดง
- undo() = ตามชื่อ function
- redo() = ตามชื่อ function
- setColorStroke(color: Int) = ใส่สีเส้นวาด เป็น เช่น 0xFF000000
- setStrokeWith(strokeWith: Float) = ใส่ขนาดเส้นวาด default 2.0
- setBackgroundColorDraw(colorId: Int) = ใส่สี background เช่น R.color.color_white (กรณีใส่เป็นรูป(imagePath) function นี้จะไม่ทำงาน)

#### Support  Version
- **Android Version(SDK) :  6.0 or later**

&copy; 2020 Khuntheeranai
