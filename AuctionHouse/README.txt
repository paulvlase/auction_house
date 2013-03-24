Ghennadi                                                                   Vlase
Procopciuc                                                                 Paul













                                     Tema 1
                                      IDP

















                                                                    [ pagina 1 ]














Cuprins:
    1.Continut arhiva ........................................................ 3
    2.Rulare ................................................................. 3
        2.1 Compilarea surselor .............................................. 3
        2.2 Executia programului ............................................. 3
    3.Testare ................................................................ 4
    4.Implementare ........................................................... 4













                                                                    [ pagina 2 ]
1.Conținut arhivă

    src              Dosar cu sursele proiectului
    resources        Resurse aditionale necesare pentru rularea proiectului
    build.xml        Fișie de build
    demands.list     Lista de oferte a cumpărătorului
    login.conf       Conturi utilizate în cadrul aplicației
    supplies.list    Lista de oferte a vânzătorului

    README           Fișierul curent

2.Rulare

    Compilarea surselor :
    ant

    Rularea proiectului :
    ant Main

    //TODO : verifica

                                                                    [ pagina 3 ]

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
    - Commander (webServiceClient, clasele din pachetul guiItems)

    Comunicarea între module se face cu ajutorul unor notificări. Pachetele
    prin intermediul cărora se transmit date sunt prezente în pachetul data.

    //TODO Utilizator github
    //TODO adresa proiect online

    Credențiale utilizate în interfața grafică :
        Usernmae : unix140
        Password : marmota
        
6. Acces surse :
    În dezvolatare surselor am folosit git, pe GitHub. Pentru a observa modul în
    care a evoluat dezvoltarea surselor se va accesa contul de pe GitHub :
    
    Username : IDPdummyAccount
    Password : IDPdummyAccount1
    
6. Bonus :

    - Interfață bilingvă cu posibilitate de extindere
    - Interfață îmbunătățită
    - Posibilitatea de a adăuga/șterge servicii în mod dinamic
    - Posibilitatea de editare a profilului utilizatorului
    - Adăugarea avatarului în setări cu posibilitatea de încărcarea unei poze
    - Un utilizator poate accesa ambele conturi atât vânzător cât și producător