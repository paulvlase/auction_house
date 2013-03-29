Ghennadi                                                                   Paul
Procopciuc                                                                 Vlase













                                     Tema 1
                                      IDP

















                                                                    [ pagina 1 ]












Cuprins:
    1.Continut arhivă ........................................................ 3
    2.Rulare ................................................................. 3
    3.Organizarea surselor ................................................... 3
    4.Testare ................................................................ 4
    5.Implementare ........................................................... 4
    5.Acces surse ............................................................ 5
    5.Bonus .................................................................. 5














                                                                    [ pagina 2 ]
1. Conținut arhivă

    src              Dosar cu sursele proiectului
    resources        Resurse aditionale necesare pentru rularea proiectului
    build.xml        Fișie de build
    demands.list     Lista de oferte a cumpărătorului
    login.conf       Conturi utilizate în cadrul aplicației
    supplies.list    Lista de oferte a vânzătorului

    README           Fișierul curent

2. Rulare

    Compilarea surselor :
    ant

    Rularea proiectului :
    ant Main

3. Organizarea surselor

    Proiectul este organizat în 11 pachete, după cum urmează :

    app                 Punctul de lansare a proiectului
    config              Fișiere de configurare
    data                Clase utilizate pentru transferul mesajului între module
    gui                 Intefața grafică
        gui.items       Suită de clase utilizate în meniurile din aplicație
        gui.spantable   Ajută la crearea unui tabel cu celule unite
    guiBuilder          Clase și forme generate cu JFormDesigner
    interfaces          Interfețele utilizate în proiect
    mediator            Modulul mediator
    network             Modulul rețea
    webServiceClient    Modulul clientului web
                                                                    [ pagina 3 ]

4. Testare

    Sistem de operare : Ubuntu 11.10 / Windows 7
    IDE               : Eclipse [Juno / Indigo]
    Plugin-uri        : EGit, JFormDesigner
    Testare           : mockup-uri scrise de noi

    Pentru simula modulele : Network, Mediator și WebServiceClient am create 3
    module de mockup (a se vedea pachetele ce sunt răspunzătoare de aceste
    module). În cadrul acestor module am apelat la SwingWorkers, care ne ajută
    în generarea datelor într-un mod aleator.

5. Implementare

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
        Pentru a rezolva tema am apelat de pattern-urile prezentate în laborator:
    - MVC (Crearea și controlarea interfeței grafice)
    - Mediator (A se vedea modulul mediator)
    - Commander (generarea evenimentelor pentru testate in Network si 
    WebServiceClient clasele din pachetul guiItems)
    - State (Prelucrarea pachetelor Service)
    - Adapter (Pentru a putea publica si prelucra pachetele Service in
    metoda process din SwingWorker) 

    Comunicarea între module se face cu ajutorul unor notificări. Pachetele
    prin intermediul cărora se transmit date sunt prezente în pachetul data.


                                                                    [ pagina 4 ]

    Credențiale utilizate în interfața grafică :
        Usernmae : unix140
        Password : marmota

6. Acces surse
    În dezvolatare surselor am folosit git (GitHub). Pentru a observa modul în
    care a evoluat dezvoltarea surselor se va accesa contul de pe GitHub :

    Username : IDPdummyAccount
    Password : IDPdummyAccount1

    Adresă proiect : https://github.com/paulvlase/auction_house

7. Bonus

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








                                                                    [ pagina 5 ]
