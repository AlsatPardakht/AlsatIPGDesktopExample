<p align="center">
  <a href="" rel="noopener">
 <img width=200px height=200px src="./logo.png" alt="Project logo"></a>
</p>

<h3 align="center">Alsat IPG Desktop Example</h3>

<div align="center">

[![Status](https://img.shields.io/badge/status-active-success.svg)]()
[![GitHub Issues](https://img.shields.io/github/issues/AlsatPardakht/AlsatIPGDesktopExample.svg)](https://github.com/AlsatPardakht/AlsatIPGDesktopExample/issues)
[![GitHub Pull Requests](https://img.shields.io/github/issues-pr/AlsatPardakht/AlsatIPGDesktopExample.svg)](https://github.com/AlsatPardakht/AlsatIPGDesktopExample/pulls)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](/LICENSE)

</div>

---

<div dir="rtl">

<p align="center">
یک مثال ساده از نحوه استفاده کردن کتابخانه <a href="https://github.com/AlsatPardakht/AlsatIPGDesktop">AlsatIPGDesktop</a> در پروژه های Desktop
    <br> 
</p>



## 📝 فهرست

- [درباره](#about)
- [نحوه استفاده](#usage)
- [ساخته شده با استفاده از](#built_using)

## 🧐 درباره <a name = "about"></a>
<p dir="rtl">
در این پروژه از کتابخانه <a href="https://github.com/AlsatPardakht/AlsatIPGDesktop">AlsatIPGDesktop</a> برای پرداخت صورت حساب به صورت اینترنتی استفاده شده است .
<br>
برای مطالعه بیشتر در مورد api مستقیم IPG آلسات پرداخت می توانید به لینک زیر مراجعه کنید :
</p>
<a href="https://www.alsatpardakht.com/TechnicalDocumentation/191">🌐 مستندات فنی IPG های مستقیم آلسات پرداخت</a><br>
این پروژه با استفاده از زبان برنامه نویسی کاتلین و جاوا نوشته شده است که می توانید از سورس کد های آن استفاده کنید .

## 🎈 نحوه استفاده <a name="usage"></a>
پس از این که مراحل <a href="https://github.com/AlsatPardakht/AlsatIPGDesktop#-%D8%B4%D8%B1%D9%88%D8%B9-%D8%A8%D9%87-%DA%A9%D8%A7%D8%B1-">شروع به کار</a> کتابخانه
<a href="https://github.com/AlsatPardakht/AlsatIPGDesktop">AlsatIPGDesktop</a>
را انجام دادید می توانید مراحل استفاده از کتابخانه که در ادامه آورده شده را انجام بدهید .
<br>

### مرحله اول : ساختن نمونه از کلاس AlsatIPG

با استفاده از دستور زیر می توانید یک نمونه از کلاس AlsatIPG بسازید و با کمک این نمونه کار های پرداخت را انجام دهید :
</div>

<details open>
    <summary>Kotlin</summary>

```Kotlin
private val alsatIPG = AlsatIPG.getInstance(httpLogging = false)
```

</details>

<details>
    <summary>Java</summary>

```java
private AlsatIPG alsatIPG = AlsatIPG.getInstance(false);
```

</details>

<div dir="rtl">
مقدار false ورودی تابع getInstance به این منظور است که اطلاعات درخواست های http در  Logcat چاپ نشود (در زمان انتشار پروژه باید این مقدار false باشد ولی در هنگام debug می تواند  true باشد) 

<br>

### مرحله دوم : پیاده سازی observer ها
در این مرحله کافی است از LiveData های PaymentSignStatus و PaymentValidationStatus را observe کنید :

</div>

<details open>
    <summary>Kotlin</summary>

```kotlin
alsatIPG.paymentSignStatus.observeForever { paymentSignResult ->
    
}

alsatIPG.paymentValidationStatus.observeForever { paymentValidationResult ->
    
}
```
</details>

<details>
    <summary>Java</summary>

```java
alsatIPG.getPaymentSignStatus().observeForever(new Observer<PaymentSignResult>() {
    @Override
    public void onChanged(PaymentSignResult paymentSignResult) {
        
    }
});

alsatIPG.getPaymentValidationStatus().observeForever(new Observer<PaymentValidationResult>() {
    @Override
    public void onChanged(PaymentValidationResult paymentValidationResult) {
        
    }
});
```

</details>

<div dir="rtl">
این دو LiveData نتیجه sign شدن پرداخت و validation پرداخت را به شما بر می گرداند .

دقت کنید Observer ها به صورت خودکار حذف نمی شوند و باید به صورت دستی وقتی دیگر احتیاجی به آنها ندارید حذف شوند :

</div>

<details open>
    <summary>Kotlin</summary>

```kotlin
alsatIPG.paymentSignStatus.removeAllObservers()
alsatIPG.paymentValidationStatus.removeAllObservers()
```
</details>

<details>
    <summary>Java</summary>

```java
alsatIPG.getPaymentSignStatus().removeAllObservers();
alsatIPG.getPaymentValidationStatus().removeAllObservers();
```

</details>
<br><br>

<div dir="rtl">

### مرحله سوم : sign کردن پرداخت
برای شروع sign پرداخت کافی است با کمک تابع های signMostaghim یا signVaset موجود در کتابخانه این کار  را انجام دهید :
</div>

<details open>
    <summary>Kotlin</summary>

```Kotlin
val paymentType = PaymentType.Mostaghim
when (paymentType) {
    PaymentType.Mostaghim -> alsatIPG.signMostaghim(
        Api = API,
        Amount = 10_000,
        InvoiceNumber = "123456",
        RedirectAddress = "http://www.example.com/some_path"
    )
    PaymentType.Vaset -> alsatIPG.signVaset(
        Api = API,
        Amount = 20_000,
        RedirectAddress = "http://www.example.com/some_path",
        Tashim = emptyList(),
        InvoiceNumber = "123456"
    )
}
```

</details>

<details open>
    <summary>Java</summary>

```java
PaymentType paymentType = Mostaghim;
switch (paymentType) {
    case Mostaghim: {
        alsatIPG.signMostaghim(
                10_000,//Amount
                API,//Api
                "123",//InvoiceNumber
                "http://eample.com"//RedirectAddress
        );
        break;
    }
    case Vaset: {
        alsatIPG.signVaset(
                20_000,//Amount
                API,//Api
                "http://eample.com",//RedirectAddress
                "123",//InvoiceNumber
                new ArrayList<TashimModel>()//Tashim
        );
        break;
    }
}
```

</details>

<div dir="rtl">

- مقدار paymentType می تواند با توجه به نیاز شما PaymentType.Mostaghim یا PaymentType.Vaset باشد .

- دقت کنید که مثال بالا بصورت دستی نوع پرداخت را وارد کرده  (شما می توانید با توجه به نیاز خود از یکی یا  هر دو حالت پرداخت به صورت داینامیک استفاده کنید)

- مقدار API همان کلید دریافتی شما در وب سایت آلسات پرداخت می باشد که برای دریافت آن ابتدا باید در وب سایت ثبت نام کنید و پس از مراحل احراز هویت این کلید به شما داده می شود .

- مقدار Amount همان مبلغ قالب پرداخت به ریال است .

- مقدار InvoiceNumber همان شماره سفارش شما است .

- مقدار RedirectAddress همان آدرس دیپ لینک به اکتیویتی شماست که در فایل AndroidManifest.xml وارد کردید .

- مقدار Tashim لیست کسانی است  که مبلغ پرداختی به حساب آنها واریز خواهد شد (پرداخت بصورت واسط)

پس از فراخوانی تابع sign نتایج این فراخوانی از طریق paymentSignStatus که observe کرده بودید در دسترس است :

</div>

<details open>
    <summary>Kotlin</summary>

```Kotlin
alsatIPG.paymentSignStatus.observeForever { paymentSignResult ->
    when {
        paymentSignResult.isSuccessful -> {
            println("successful")
            println(paymentSignResult)

            try {
                if (Desktop.isDesktopSupported() &&
                    Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)
                ) {
                    Desktop.getDesktop().browse(URI(paymentSignResult.url!!))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            println(">> validation <<")
            print("enter tref : ")
            val tref = readln()
            print("enter iN : ")
            val iN = readln()
            print("enter iD : ")
            val iD = readln()
            print("enter PayId : ")
            val PayId = readln()

            testValidation(tref = tref, iN = iN, iD = iD, PayId = PayId)
            readln()
        }
        paymentSignResult.isLoading -> {
            println("loading")
        }
        else -> {
            println("error : " + paymentSignResult.error?.message)
        }
    }
}
```

</details>

<details>
    <summary>Java</summary>

```java
alsatIPG.getPaymentSignStatus().observeForever(new Observer<PaymentSignResult>() {
    @Override
    public void onChanged(PaymentSignResult paymentSignResult) {
        if (paymentSignResult.isLoading()) {
            System.out.println("loading");
        } else if (paymentSignResult.isSuccessful()) {
            System.out.println("successful");
            System.out.println(paymentSignResult);

            try {
                if (
                    Desktop.isDesktopSupported() &&
                    Desktop.getDesktop().isSupported(Desktop.Action.BROWSE) &&
                    paymentSignResult.getUrl() != null
                ) {
                    Desktop.getDesktop().browse(new URI(paymentSignResult.getUrl()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println(">> validation <<");
            System.out.print("enter tref : ");
            String tref = scanner.nextLine();
            System.out.print("enter iN : ");
            String iN = scanner.nextLine();
            System.out.print("enter iD : ");
            String iD = scanner.nextLine();
            System.out.print("enter PayId : ");
            String PayId = scanner.nextLine();

            testValidation(tref, iN, iD, PayId);
            scanner.nextLine();
        } else {
            if (paymentSignResult.getError() != null)
                System.out.println("error : " + paymentSignResult.getError().getMessage());
        }
    }
});
```

</details>

<div dir="rtl">

این observer وضعیت های موفق بودن یا لودینگ یا ارور تابع signMostaghim و یا signVaset را به شما تحویل می دهد .

همچنین می توانید از paymentSignStatusAsFlow بجای paymentSignStatus استفاده کنید که وضعیت sign را بصورت Flow به شما تحویل می دهد .(فقط برای Kotiln این امکان وجود دارد)
<br>
دقت کنید در زمان موفق بودن sign پرداخت شما باید با استفاده از url موجود در نتیجه یک صفحه وب برای هدایت شدن کاربر به صفحه پرداخت شاپرک باز کنید ( کد داخل بخش try و catch برای باز کردن یک صفحه وب به منظور وارد کردن اطلاعات حساب و انجام پرداخت می باشد )
<br>
<br><br>

### مرحله چهارم : validation کردن پرداخت

برای این که بتوانید برای پرداخت validation انجام بدهید کافی است تابع validationMostaghim یا validationVaset را فراخوانی کنید :

</div>

<details open>
    <summary>Kotlin</summary>

```Kotlin
//val data = URI("http://eample.com/?tref=637829347727645295&iN=123&iD=2022/03/15%2009:52:54")
when (paymentType) {
    PaymentType.Mostaghim -> alsatIPG.validationMostaghim(
        Api = API,
//      data = data
        tref = tref,
        iN = iN,
        iD = iD,
        PayId = PayId
    )
    PaymentType.Vaset -> alsatIPG.validationVaset(
        Api = API,
//      data = data
        tref = tref,
        iN = iN,
        iD = iD,
        PayId = PayId
    )
}
```

</details>

<details>
    <summary>Java</summary>

```java
//URI data = URI("http://eample.com/?tref=637829347727645295&iN=123&iD=2022/03/15%2009:52:54")
switch (paymentType) {
    case Mostaghim: {
        alsatIPG.validationMostaghim(
                API,//Api
//              data//data
                tref,//tref
                iN,//iN
                iD,//iD
                PayId//PayId
        );
        break;
    }
    case Vaset: {
        alsatIPG.validationVaset(
                API,//Api
//              data//data
                tref,//tref
                iN,//iN
                iD,//iD
                PayId//PayId
        );
        break;
    }
}
```

</details>

<div dir="rtl">

- مقدار paymentType می تواند با توجه به نیاز شما PaymentType.Mostaghim یا PaymentType.Vaset باشد .

- دقت کنید که مثال بالا بصورت دستی نوع پرداخت را وارد کرده  (شما می توانید با توجه به نیاز خود از یکی یا  هر دو حالت پرداخت به صورت داینامیک استفاده کنید)

- مقدار API همان کلید دریافتی شما در وب سایت آلسات پرداخت می باشد .

- مقدار data همان URI هست که از سمت شاپرک به RedirectAddress شما بازگشت داده می شود و تابع validationMostaghim یا validationVaset با  استفاده از این اطلاعات معتبر بودن پرداخت را برسی می کند (در مثال بالا یک نمونه از این URI نمایش داده شده البته می توانید مستقیما مقدار های tref  و iN و iD و PayId را به جای data به تابع بدهید)

<br>
پس از آن که کاربر پرداخت را به درستی انجام داد یا به هر دلیلی موفق به پرداخت نشد شاپرک کاربر را به آدرس RedirectAddress که در زمان sign پرداخت وارد کرده بودید هدایت می کند و سپس شما می توانید اطلاعات پرداخت را در RedirectAddress دریافت کنید و سپس به کاربر خود بفرستید و با تابع validationMostaghim یا validationVaset می توانید validation انجام بدهید .
<br>
پس از فراخوانی تابع validation نتایج این فراخوانی از طریق paymentValidationStatus که observe کرده بودید در دسترس است :
</div>

<details open>
    <summary>Kotlin</summary>

```Kotlin
alsatIPG.paymentValidationStatus.observeForever { paymentValidationResult ->
    when {
        paymentValidationResult.isSuccessful -> {
            println("successful")
            println("payment Validation Success data = ${paymentValidationResult.data}")
            if (
                (paymentValidationResult.data?.PSP?.IsSuccess == true) &&
                (paymentValidationResult.data?.VERIFY?.IsSuccess == true)
            ) {
                println("money transferred")
            } else {
                println("money has not been transferred")
            }
        }
        paymentValidationResult.isLoading -> {
            println("loading")
        }
        else -> {
            println("error : " + paymentValidationResult.error?.message)
        }
    }
}
```

</details>

<details>
    <summary>Java</summary>

```java
alsatIPG.getPaymentValidationStatus().observeForever(new Observer<PaymentValidationResult>() {
    @Override
    public void onChanged(PaymentValidationResult paymentValidationResult) {
        if (paymentValidationResult.isSuccessful()) {
            System.out.println("successful");
            System.out.println("payment Validation Success data = " + paymentValidationResult.getData());
            if (
                (paymentValidationResult.getData() != null) &&
                    (paymentValidationResult.getData().getPSP().getIsSuccess()) &&
                    (paymentValidationResult.getData().getVERIFY().getIsSuccess())
            ) {
                System.out.println("money transferred");
            } else {
                System.out.println("money has not been transferred");
            }
        } else if (paymentValidationResult.isLoading()) {
            System.out.println("loading");
        } else if (paymentValidationResult.getError() != null) {
            System.out.println("error : " + paymentValidationResult.getError().getMessage());
        }
    }
});
```

</details>

<div dir="rtl">

این observer وضعیت های موفق بودن یا لودینگ یا ارور تابع validationMostaghim یا validationVaset را به شما تحویل می دهد .

همچنین می توانید از paymentValidationStatusAsFlow بجای paymentValidationStatus برای گرفتن وضعیت valiadation استفاده کنید که وضعیت را بصورت Flow به شما تحویل می دهد(فقط برای Kotiln این امکان وجود دارد)
<br>
همچنین در زمانی که پرداخت موفق بوده فیلد data موجود در نتیجه اطلاعاتی در مورد پرداخت را به شما می دهد که می توانید از آن استفاده کنید .

<br>

### ⚠️ توجه 1 :
دقت کنید که موفق بودن نتیجه تابع validation به این معنی نیست که پول از حساب کاربر به حساب شما انتقال یافته ! برای کاملا مطمئن شدن از انتقال پول حتما از دستور زیر برای برسی استفاده کنید :

</div>

<details open>
    <summary>Kotlin</summary>

```Kotlin
if (
    (paymentValidationResult.data?.PSP?.IsSuccess == true) &&
    (paymentValidationResult.data?.VERIFY?.IsSuccess == true)
) {
    log("money transferred")
} else {
    log("money has not been transferred")
}
```

</details>

<details open>
    <summary>Java</summary>

```java
if (
    (paymentValidationResult.getData() != null) &&
        (paymentValidationResult.getData().getPSP().getIsSuccess()) &&
        (paymentValidationResult.getData().getVERIFY().getIsSuccess())
) {
    System.out.println("money transferred");
} else {
    System.out.println("money has not been transferred");
}
```

</details>

<div dir="rtl">

اطلاعات موجود در فیلد data شامل :
- شماره فاکتور ( PayId )
- مبلغ پرداختی به ریال ( PSP.Amount )
- تاریخ فاکتور ( PSP.InvoiceDate )
- شماره فاکتور ( PSP.InvoiceNumber )
- موفقیت پرداخت در سمت بانک ( PSP.IsSuccess )
- کد بازرگان ( PSP.MerchantCode )
- پیام سمت بانک ( PSP.Message )
- شماره مرجع ( PSP.ReferenceNumber )
- کد ترمینال ( PSP.TerminalCode )
- شماره ردیابی ( PSP.TraceNumber )
- تاریخ معامله ( PSP.TransactionDate )
- شناسه مرجع تراکنش ( PSP.TransactionReferenceID )
- شماره کارت هش شده پرداخت کننده ( PSP.TrxHashedCardNumber )
- شماره کارت ماسک پرداخت کننده ( PSP.TrxMaskedCardNumber )
- شماره کارت هش شده پرداخت کننده ( VERIFY.HashedCardNumber )
- موفقیت انجام عملیات پرداخت ( VERIFY.IsSuccess )
- شماره کارت ماسک پرداخت کننده ( VERIFY.MaskedCardNumber )
- پیام عملیات پرداخت ( VERIFY.Message )
- شماره مرجع شاپرک ( VERIFY.ShaparakRefNumber )

### ⚠️ توجه ۲ :
دراین مثال برای سادگی کار فرایند  validation سمت پروژه صورت گرفته است .
توصیه ما این است که فرایند validation  را سمت سرور خود انجام بدهید و اطلاعات سمت بانک را به پروژه نفرستید که امنیت پروژه و پرداخت شما را خیلی بالا خواهد برد .
<br>
برای این کار کافی است در زمان sign کردن پرداخت در ارگیومنت RedirectAddress آدرس وب سایت خود برای validation را وارد کنید  . در این صورت شاپرک اطلاعات validation را به آدرس وارد شده خواهد فرستاد و شما می توانید با استفاده از
api آلسات پرداخت در وب سایت خود اعتبار پرداخت را برسی کنید و بعد برسی اعتبار پرداخت خرید کاربر را تایید یا رد کنید و سپس با استفاده یک api در وبسایت خود این صحت اعتبار را در پروژه Desktop دریافت کنید .
<br>
در صورت استفاده از روش validation سمت سرور کاربر (هکر) نمی تواند ادعا کند پرداخت موفق  داشته (چون دسترسی به validation سمت سرور شما را ندارد) در حالی که در روش معمولی کاربر(هکر) ممکن است با ایجاد تغییراتی در پروژه شما یا با روش های دیگر موفق شود این کار را انجام دهد .

<br>
سورس کد کامل  در فایل های زیر آورده شده است :
<br>

- <a href="https://github.com/AlsatPardakht/AlsatIPGDesktopExample/blob/master/src/main/kotlin/com/alsatpardakht/Main.kt">Main.kt</a>
- <a href="https://github.com/AlsatPardakht/AlsatIPGDesktopExample/blob/master/src/main/java/com/alsatpardakht/Main.java">Main.java</a>

## ⛏️ ساخته شده با استفاده از  <a name = "built_using"></a>

</div>


- [Kotlin](https://kotlinlang.org/) - programming language
- [Java](https://www.java.com/en/) - programming language
- [AlsatIPGDesktop](https://github.com/AlsatPardakht/AlsatIPGDesktop) - payment client library
- [AlsatIPGCore](https://github.com/AlsatPardakht/AlsatIPGCore) - payment core library
