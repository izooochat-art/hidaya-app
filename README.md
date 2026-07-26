# آلة حاسبة — CalculatorApp

مشروع أندرويد (Kotlin) لآلة حاسبة بسيطة بخلفية صورة شخصية.

## كيف تفتح المشروع
1. افتح Android Studio.
2. اختر Open، وحدد مجلد `CalculatorApp` (هذا المجلد بالكامل).
3. Android Studio رح يولّد تلقائياً ملفات Gradle Wrapper (gradlew) لأول مرة عند فتح المشروع — انتظر لحتى تخلص عملية Sync.
4. اضغط Run ▶ لتشغيل التطبيق على المحاكي أو جهاز حقيقي.

## بنية المشروع
- `app/src/main/java/com/example/calculatorapp/MainActivity.kt` — منطق الحاسبة (جمع، طرح، ضرب، قسمة، نسبة مئوية، إشارة موجب/سالب).
- `app/src/main/res/layout/activity_main.xml` — تصميم الشاشة (الصورة كخلفية + طبقة تعتيم خفيفة لوضوح الأرقام + شاشة العرض + الأزرار).
- `app/src/main/res/drawable/bg_photo.jpg` — الصورة المرفقة، مستخدمة كخلفية للتطبيق.
- `app/src/main/res/values/styles.xml` — أشكال أزرار الأرقام والعمليات.

## ملاحظة
الصورة المستخدمة كخلفية هي الصورة يلي رفعتها. إذا بدك تبدلها، بس بدّل الملف
`app/src/main/res/drawable/bg_photo.jpg` بصورة تانية بنفس الاسم.
