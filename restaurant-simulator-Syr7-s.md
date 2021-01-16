## Fibabanka Java Bootcamp 3. Hafta Ödevi Bilgilendirme Dosyası

​	Ödev bir restaurant simülasyon programıdır ve Java da Multi thread kullanılarak hazırlanmıştır. Restoranda 5 masa 3 garson ve 2 şef bulunmaktadır. Restorana sırası ile müşteri gelmekte ve restoranda 5 masa da dolu ise diğer gelen müşteriler kuyruk da beklemektedir. Yemek siparişini verip ve teslim alan ya da sipariş verdiği halde belli süre sonunda siparişi alamayan müşteri restoranda ayrılır ve ayrıldığı gibi başka bir müşteri restorana girer. (Bu uygulamada Müşteri -> Customer, Garson -> Waiter, Şef  - Aşçı -> Cook olarak adlandırılmıştır.)

​	Yukarıda bahsedilen bilgiler doğrultusunda 4 ayrı paket oluşturulmuştur. Program ilk olarak main paketi içindeki Main classından başlar. Main classında ilk satırda RestaurantAppContext adlı class bulunmaktadır. Bu sınıf içinde 3 adet obje dönen metot vardır. Bu sınıflara ihtiyaç duyan metorlar RestaurantAppContext ile bir nesne oluşturup kullanabilir.Bunun amacı sınıflar arasında bağımlılığı en aza indirmek ve bu nesnelere ihtiyac duyan sınıfların çağrıldıkları yere parametre olarak verilerek kullanılacak sınıf içinde Constructor injection yöntemi ile ilgil nesne set edilerek kullanılabilmesi amaçlanmıştır.

​	Main classında uygulama ilk çalıştırıldığında Restoran hakkında bilgi alması amacı ile bir bildiri yazısı ile karşılaşır. Stream ile de restoran menüsünde bulunan yiyecekler tutulmaktadır.

​	 Food objesini kullanabilmek için restaurantAppContext classındaki food metodu çağrılır ve bu metot Singleton bir obje döner ve bu obje sadece bir kere oluşturulur ve yiyecek listesi hazır hale gelir. Food classı singleton design pattern kullanarak tasarlanmış ve thread safe hale getirilmiştir.Birden fazla thread bu objeyi kullanamayacak. Bir thread işini bitirecek ondan sonra başka bir threadin  kullanıma açık hale gelecektir. Food classı ilk oluşturulduğunda ek olarak bir de food listesi oluşacaktır bu listede farklı ülkelerden bazı yemekler bulunmaktadır. Main classında da Stream oluşturularak var olan yiyecekler ekrana bastırılıyor.

​	Uygulama RestaurantAppContext classındaki restaurant metodu çağrılarak döndüğü obje Restaurant classındaki constructora parametre olarak veriliyor. Restaurant metodu da restaurantSimulation metodunu parametre olarak alıyor, restaurantSimulation metoduda bir food parametre alıyor ve bu parametreler constructor injection yöntemi ile kullanımı ihtiyaç duyulan yerlerde ilgili referanslara set edildikten sonra kullanılabilir.

​	Bu class içinde constructor food objesini parametre olarak almakta ve çağrılan yer tarafından set edilmektedir. Restaurant classındaki constructor restaurantSimulation objesini referans olarak aldıktan sonra RestaurantSimulation classında bulunan startSimulation metodu ile ExecutorService (Customer,Waiter ve Cook) ve Semaphore(Customer,Waiter ve Cook) oluşturmaktadır. Semaphore (Ortak kullanılan objeler üzerinde threadlerin sıra ile işlem yapmasına olanak sağlar.) Thread objeler arasında senkronizasyonu sağlamak amacı ile kullanılması amaçlanmıştır. Threadlerin yönetimi amacıyla da Executor Service kullanılmıştır.  startSimulation metodu ile de simulasyon işlemleri başlatılmaktadır. newFixedThreadPool(int nThreads) : (Threads değişkenine verilen değer kadar Thread oluşturup havuzda saklar), metodu kullanılmıştır. Bu yapılar da execute yerine submit metodu kullanılmıştır.(Submit metodu ise Runnable ve Callable tipinden değerler alır ve geriye Future tipinde dönüş yapar. Bildiğiniz gibi Runnable arayüzünün run metodu dönüş tipine sahip değildir, istenirse submit metodu içerisine Runnable arayüzü ve dönüş tipini verebiliriz.)

- İlk for döngüsü Restoran kapasitesi ne kadar müşteri alıyorsa günlük o kadar çalışacaktır. executorServiceCustomer ile  de 5 adet (masa sayısı)  kadar müşteri restoran yer alacaktır. Ondan sonrakilerde restorandan müşteri çıktıkça restorana girecektir.Submit metodu ile de customer objesi oluşturulmakta ve müşteri adı müşteri sıra numarası semaphore ve food objelerini parametre almakta Customer constructorunda set ettikten sonra gerekli yerlerde kullanılmaktadır.
- İkinci for dongusu executorServiceWaiter submit metodu ile ilgili Waiter classı için gerekli olan işlemleri yapılacaktır.
- Son for döngüsü executorServiceCokk submit metodu ile ilgili Cook classı için gerekli işlemler yapılacaktır.
- Her executorService shutdown metodu ile sonlandırılmıştır. (Shutdown: Executor service içinde bulunan işlerin bitmesi beklenir ve başka iş alınmazBeklenen işler bittiğinde thread yok edilir.)
- awaitTermination metodu ile zaman sınırlaması belirlendi.Maksimum beklenecek zamanı belirtildi yani tüm görevler tamamlanana kadar bekle Eğer parametre olarak verilen 1 gün de tamalmanmaz ise uygulamayı sonlandır.

RestaurantSimulation sınıfı startSimulation metotu bu kadar. Şimdi Restaurant classı içindeki Customer, Waiter ve Cook için gerekli olan metotları bu sınıf içinde tanımladık. Bu metotlardan bazıları Fonksiyonel interface olarak tanımlanmıştır. 

- ```java
   static Supplier<Boolean> enterToTheRestaurant = () {};
  ```

  Yukarıdaki ön tanımlı functional interface Boolean tipte bir değer alıp onu dönmektedir. Bu metot Customer classında restoranda bir müşteri işi bitip çıktıktan sonra yeni bir müşterinin restorana girmesi için kullanılacak metot dur. Eğer restoranda müşteri sayısı 5 olunca true dönecektir.

- ```java
   static void customerLeaveRestaurant() { customerNumber--; }
  ```

  Bu metot ise Restoranda müşteri belirli bir süre sonra geçtikten sonra müşterinin restoranda ayrılması durumunda Restorandaki müşteri sayısını 1 azaltmaktadır.

  ```java
  static void customerPlaceOrder(int orderNum, String food)
  ```

  customerPlaceOrder ile müşteri sipariş vermektedir. 2 parametre alır, ilk parametre müşteri sipariş numarası. İkinci parametre ise müşterinin sipariş ettiği yiyecektir. OrderList ile sipariş listesi ve orderPlaced ise verilen siparişi tutmaktadır.

  ```java
  static Predicate<Waiter> orderAvailable = (waiter) -> { /* ... */ }
  ```

  Yukarıdaki ön tanımlı functional interface ise sipariş kontrolü yapılıyor. Eğer müşteri siparişi var ise waiterOrderList e sipariş ve waiterOrderPlaced a sipariş bilgileri ekleniyor. waiterOrderClaim ( garson aşçıdan sipariş talebinde bulunacaktır.) ve bu bir hashMapdir.waiterOrderNumClaim(Garson Sipariş sıra numara) ise bir hashMap dir. Eğer customer siparişi var ise bu metot true döner ve gerekli işlemler yapılır.

- ```java
  static Predicate<Cook> cookOrderAvailable = (cook) -> { /* ... */ }
  ```

  Cook (Chef) ise Waiter bir sipariş talebi var mı diye kontrol eder. Eğer müşteri sipariş talebinde bulunmuş ise waiterOrderList ile sipariş numarası çekilir. cookOrderClaim (Aşçi Sipariş Talebi) bunda ise aşçı siparişleri kendi sipariş listesine alır ve buradan siparişleri hazırlamaya başlar.cookOrderNumClaim ise sipariş talep numaralarını tutmaktadır.

- ```java
  static Function<Waiter, Integer> waiterGetOrderNum = (waiter -> waiterOrderNumClaim.remove(waiter));
  ```

  waiterGetOrderNum ise Waiter classında waiter sipariş numarasını getirmektedir. Kendi classın da ki orderNum a parametre olarak almaktadır. Bunu yaparken de waiter siparişi listeden çıkarmaktadır.

- ```java
   static Function<Waiter, String> waiterGetOrder = (waiter -> waiterOrderClaim.remove(waiter));
  ```

  waiterGetOrder  ise waiter classında food almak için kullanılacaktır yani Waiter classında food isminde tanımlanan field tarafından kullanılacaktır.Sonra bu food field ı başka bir fonksiyona parametre olarak verilecektir.

- ```java
  static Function<Cook, Integer> cookGetOrderNum = (cook -> cookOrderNumClaim.remove(cook));
  ```

  cookGetOrderNum ise garson tarafından getirilen siparişler aşçı sipariş listesine eklenmiştir ve aşçılarda bu eklenen bilgiler doğrultusunda siparişleri alacaktır.Bu metot ile de aşçı kendi listesindeki sipariş numarasını alacaktır.

- ```java
  static Function<Cook, String> cookGetOrder = (cook -> cookOrderClaim.remove(cook));
  ```

  cookGetOrder ile de sipariş ne ise onu alacaktır.

- ```java
  static void cookOrderCompleted(Cook cook, int orderNum, String food){/* ... */}
  ```

  Aşçı (Chef) siparişleri tamamladıktan sonra bu metodu çalıştırmakta ve tamamlanan siparişleri cookCompletedOrders adlı listeye eklemektedir. Bu listeye tamamlanan sipariş numarası eklenmektedir.

- ```java
  static Predicate<Integer> checkWaiterOrderStatus = (orderNum) -> cookCompletedOrders.contains(orderNum);
  ```

  Bu metot ile Waiter classı aşçı (chef) tarafından tamamlanan siparişleri kontrol etmektedir. Eğer true döner ise garson tarafından siparişin tamamlandığını anlamaktadır.

- ```java
  static void waiterOrderCompleted(Waiter waiter, int orderNum) 
  ```

  checkWaiterOrderStatus metodu true döndükten sonra bu metot çalışır. waiterOrderCompleted adli listeye tamamlanan sipariş eklenmektedir.

- ```java
   static Predicate<Integer> checkOrderCompleted = (orderNum) -> waiterCompletedOrders.contains(orderNum);
  ```

  Bu metot ile customer tamamlanan siparişi kontrol etmekte eğer sipariş tamamlandıysa siparişini almaktadır. Siparişi aldıktan sonra siparişini yedikten sonra restaurant dan ayrılıyor. Alamadığında da müşteri süresi ayrılıyor ve aynı zamanda restoranda bulunma süresi de dolduğu için.

Bu yukarıdaki metotlar Customer Waiter ve Cook adlı sınıflarda kullanılmıştır. (Müşteri sayısı 10 olarak ayarlanmıştır.)

Kod çıktısı : 

```java
There are 5 tables in the restaurant.
Restaurant customer capacity is 10.
3 waiters and 2 cooks work in the Restaurant.
Food Types: 
----------------------------------------------
Soup
Fish
Ravioli
Meat
Burger
Cake
French fries
Bratwurst
Lasagna
Carpaccio
Brioche
Ratatouille
----------------------------------------------
Customer 1 is entering to Restaurant.
Customer 1 giving to order.
Customer order 1 and Meat to order
Customer 2 is entering to Restaurant.
Customer 2 giving to order.
Customer order 2 and Fish to order
Customer 3 is entering to Restaurant.
Customer 3 giving to order.
Customer order 3 and Burger to order
Customer 4 is entering to Restaurant.
Customer 4 giving to order.
Customer order 4 and Cake to order
Customer 5 is entering to Restaurant.
Customer 5 giving to order.
Customer order 5 and Bratwurst to order
Waiter 1 waiter will take the order.
Waiter named Waiter 1 received the order of Meat number 1.
Waiter 2 waiter will take the order.
Waiter named Waiter 2 received the order of Fish number 2.
Waiter 3 waiter will take the order.
Waiter named Waiter 3 received the order of Burger number 3.
Cook 1 cook will take the order.
Cook 2 cook will take the order.
Cook named Cook 2 received the order of Meat number 1
Cook named Cook 1 received the order of Fish number 2
Cook 1 completed the Fish order number 2
Cook 2 completed the Meat order number 1
Cook named Cook 2 job is over.
Cook named Cook 1 job is over.
Cook named Cook 2 received the order of Burger number 3
Cook 2 completed the Burger order number 3
Cook named Cook 2 job is over.
Waiter 1 received the completed order number 1 from the cook.
Waiter named Waiter 1 job is over.
Waiter named Waiter 1 received the order of Cake number 4.
Waiter 3 received the completed order number 3 from the cook.
Waiter named Waiter 3 job is over.
Waiter named Waiter 3 received the order of Bratwurst number 5.
Waiter 2 received the completed order number 2 from the cook.
Waiter named Waiter 2 job is over.
Waiter named Waiter 1 did not receive the order of Cake number 4 from Cook
Waiter named Waiter 1 job is over.
Waiter named Waiter 3 did not receive the order of Bratwurst number 5 from Cook
Waiter named Waiter 3 job is over.
Customer 5 could not get the order customer requested.
Customer 5 is leaving from Restaurant.
Customer 3 received the number 3 Burger order the customer wanted on time.
Customer 1 received the number 1 Meat order the customer wanted on time.
Customer 1 is eating food.
Customer 1 is leaving from Restaurant.
Customer 4 could not get the order customer requested.
Customer 4 is leaving from Restaurant.
Customer 8 is entering to Restaurant.
Customer 8 giving to order.
Customer 2 received the number 2 Fish order the customer wanted on time.
Customer 2 is eating food.
Customer 2 is leaving from Restaurant.
Customer 9 is entering to Restaurant.
Customer 9 giving to order.
Customer order 9 and Ravioli to order
Customer order 8 and Burger to order
Customer 7 is entering to Restaurant.
Customer 7 giving to order.
Customer order 7 and Fish to order
Customer 6 is entering to Restaurant.
Customer 6 giving to order.
Customer order 6 and Brioche to order
Customer 3 is eating food.
Customer 3 is leaving from Restaurant.
Customer 10 is entering to Restaurant.
Customer 10 giving to order.
Customer order 10 and Burger to order
Customer 10 could not get the order customer requested.
Customer 10 is leaving from Restaurant.
Customer 9 could not get the order customer requested.
Customer 9 is leaving from Restaurant.
Customer 8 could not get the order customer requested.
Customer 8 is leaving from Restaurant.
Customer 6 could not get the order customer requested.
Customer 6 is leaving from Restaurant.
Customer 7 could not get the order customer requested.
Customer 7 is leaving from Restaurant.
The Simulation is terminating.
```

Yukarıda bu çıktı olma sebebi Thread sınıflar içindeki sınıflarda bulunan run metotlarında ki Thread.sleep() metoduna parametre olarak verilen sürelerden kaynaklanmaktadır.