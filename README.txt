Ghennadi                                                                   Paul
Procopciuc                                                                 Vlase













                                     Tema 2
                                      IDP

















                                                                    [ pagina 1 ]










Cuprins:
    1.Conținut arhivă ........................................................ 3
    2.Rulare ................................................................. 3
    3.Organizarea surselor ................................................... 4
    4.Testare ................................................................ 4
    5.Acces surse ............................................................ 4
    6.Credențiale utilizate în interfața grafică.............................. 5
    7.GUI & Arhitectura .......................................................5
        7.1.Implementare ..................................................... 5
        7.2.Bonus ............................................................ 6
    8.NET .....................................................................6
        8.1.Implementare ..................................................... 6












                                                                    [ pagina 2 ]
1. Conținut arhivă

    AuctionHouse
        src              Dosar cu sursele proiectului
        resources        Resurse aditionale necesare pentru rularea proiectului
        build.xml        Fișie de build
        demands.list     Lista de oferte a cumpărătorului
        login.conf       Conturi utilizate în cadrul aplicației
        supplies.list    Lista de oferte a vânzătorului
    action_house.sql Date pentru test
    README           Fișierul curent

2. Rulare

    Compilarea surselor :
    ant

    Pornirea serverului Web :
    ant WebServerMockup

    Rularea interfeței grafice :
    ant Main

    O posibilă rulare :
    ant
    ant WebServerMockup
    ant Main # Logare utilizator1
    ant Main # Logare utilizator2
    ant Main # Logare utilizator3

    Notă : Pentru partea de logare în interfată grafică vezi capitolul :
    6. Credențiale utilizate în interfața grafică



                                                                    [ pagina 3 ]
3. Organizarea surselor

    Proiectul este organizat în 11 pachete, după cum urmează :

    app                     Punctul de lansare a proiectului
    config                  Fișiere de configurare
    data                    Clase utilizate pentru transferul mesajului între
                            module
    gui                     Intefața grafică
        gui.items           Suită de clase utilizate în meniurile din aplicație
        gui.spantable       Ajută la crearea unui tabel cu celule unite
    guiBuilder              Clase și forme generate cu JFormDesigner
    interfaces              Interfețele utilizate în proiect
    mediator                Modulul mediator
    network                 Modulul rețea
    states                  O serie de clase care descriu stările în care se
                            poate afla un serviciu [Service]
    webClient               Modulul clientului Web
    webServer               Clasele care încapsulează și abstractizează mesajele
                            din comunicarea cu serverul Web.
        webServer.messages  O serie de mesaje cu ajutorul cărora se realizează
                            comunicația cu serverul Web.

4. Testare

    Sistem de operare : Ubuntu 11.10 / Windows 7 / Bodhi Linux 2.3.8
    Limbaj            : Java
    IDE               : Eclipse [Juno / Indigo]
    Plugin-uri        : EGit, JFormDesigner
    Testare           : mockup-uri scrise de noi
    Baza de date      : MySql 5.5.31
    Date pentru test  : action_house.sql

5. Acces surse

    În dezvolatare surselor am folosit git (GitHub). Pentru a observa modul în
    care a evoluat dezvoltarea surselor se va accesa contul de pe GitHub :

    Username : IDPdummyAccount
    Password : IDPdummyAccount1
                                                                    [ pagina 4 ]
    Adresă proiect : https://github.com/paulvlase/auction_house


6. Credențiale utilizate în interfața grafică

        Usernmae : unix140
        Password : marmota

        Usernmae : pvlase
        Password : parola

        Usernmae : s1
        Password : s1

7. GUI & Arhitectura
    7.1. Implementare

        În cursul implementării am încercat să urmăm enunțul, iar acolo unde ni
    s-a părut că am putea aduce îmbunătățiri, am mai adăugat funcționalități. La
    capitolul interfață grafică am renunțat ușor la modelul prezentat în enunț
    în care lista de furnizori era afișată sub forma unui JList în favoarea unui
    tabel în care există celule îmbinate. Ținând cont că la cumpărător poate
    exista un singur furnizor acceptat, progress bar-ul se va extinde peste
    toate celulele care conțineau date despre toți furnizorii. La furnizori
    progresbar-ul se va extinde peste toate celulele care țin de datele
    consumatorului, dar nu va șterge restul cererilor de oferte venite din
    partea altor cumpărători.
        Pentru a rezolva tema am apelat de pattern-urile prezentate în
    laborator:
        - MVC (Crearea și controlarea interfeței grafice)
        - Mediator (A se vedea modulul mediator)
        - Commander (generarea evenimentelor pentru testate in Network si
        WebServiceClient clasele din pachetul guiItems)
        - State (Prelucrarea pachetelor Service)

                                                                    [ pagina 5 ]
        - Adapter (Pentru a putea publica si prelucra pachetele Service in
        metoda process din SwingWorker)

    Comunicarea între module se face cu ajutorul unor notificări. Pachetele
    prin intermediul cărora se transmit date sunt prezente în pachetul data.

    7.2. Bonus

    - Interfață bilingvă cu posibilitate de extindere (Opțiunea se accesează
      din fereastra Login, Language)
    - Interfață îmbunătățită
    - Posibilitatea de a adăuga/șterge servicii în mod dinamic pentru ambele
      entități
    - Posibilitatea de editare a profilului utilizatorului
    - Fereastră de Login
    - Adăugarea avatarului în setări cu posibilitatea de încărcarea unei poze
    - Un utilizator poate accesa ambele conturi atât vânzător cât și producător
    - Fereastră de înregistrare pentru utilizatori

8.NET
    8.1. Implementare

    Sursele corespunzătoare modulului de networking se găsesc în pachetul
    network. Conform enunțului procesarea mesajelor și transmiterea / primirea
    lor de pe rețea sunt două componente distincte. Partea de gestiune a
    pachetor pe rețea se face în NetworkDriver iar tratarea evenimentelor
    apărute în urma unor pachete în NetworkReceiveEvents / NetworkSendEvents.
    Structura mesajelor folosite în cadrul protocolului este definită în clasa
    Message din pachetul data, câmpurile acesteia sunt completate în funcție de
    necesitate. Un Message este serializat sub forma unui vector intrinsec de
    bytes a cărui primii 4 bytes reprezintă dimensiunea efectivă a pachetului.
    La recepționare octeții sunt interpretați ca fiind un obiect de tipul
    Message. Fiecare Serviciu din interfața grafică are asociată o stare la un

                                                                    [ pagina 6 ]
    moment dat, această stare ne ajută să generăm un pachet Message de fiecare
    dată când sunt necesare notificările celor care sunt implicați în licitație.
    Interpretarea unui mesaj se poate urmări în clasa NetworkReceiveEvents.
    Pentru mai multe detalii despre protocol se pot urmări sursele.

9.Web Client & Server

    9.1 Implementare

    Sursele corespunzătoare părții 3 a proiectului se împart în :
    o Client : 
    o Server :



























                                                                    [ pagina 7 ]