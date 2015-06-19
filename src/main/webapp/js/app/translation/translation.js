angular.module('viacnezsperk').config(['$translateProvider', function ($translateProvider) {
    $translateProvider.translations('sk', {
        login_name: 'Prihlasovacie meno',
        login_name_short: 'Prihl_ meno',
        login_password: 'Heslo',
        login_button: 'Prihlásiť sa',
        welcome: 'Vitajte',
        logout: 'Odhlásiť sa',
        your_child: 'Zadajte prihlasovacie údaje pre zobrazenie Vami podporovaného dieťaťa',
        children_list: 'Zoznam detí',
        admin: 'Admin',
        access_denied_message: 'Pre zobrazenie obsahu tejto stránky nemáte dostatočné privilégiá!',
        back_home: 'Späť na úvodnú stránku',
        add_child: 'Pridať dieťa',
        child: 'Dieťa',
        save: 'Uložiť',
        cancel: 'Zrušiť',
        name: 'Meno',
        lastname: 'Priezvisko',
        age: 'Vek',
        main_url: 'URL obrázka',
        main_content: 'Text',
        created: 'Vytvorené',
        createdBy: 'Vytvoril',
        modified: 'Upravené',
        modifiedBy: 'Upravil',
        delete: 'Zmazať',
        delete_question: 'Naozaj chcete zmazať tento záznam?',
        delete_image_question: 'Naozaj chcete zmazať fotografiu?',
        select_file: '[Vybrať foto]',
        upload_to_picasa: '[Nahrať do Picasy]',
        error_username_already_exists: 'Toto prihlasovacie meno je už obsadené',
        required: 'Povinné',
        login_error: 'Zadané používateľské meno alebo heslo je nesprávne_',
        typeMismatch_java_lang_Integer: 'Zadaná hodnota musí byť celé číslo',
        generate: 'Generovať',
        insight: 'Náhľad',
        close_insight: 'Zavrieť náhľad',
        search: 'Hľadať',
        nrOfElements: 'Počet záznamov',
        bornYear: 'Rok narodenia',
        residence: 'Bydlisko',
        socialInfo: 'Sociálna situácia',
        gallery: 'Galéria',
        viacnezsperk: 'Viac než šperk © 2013'
    });

    $translateProvider.translations('en', {

    });

    $translateProvider.preferredLanguage('sk');
}]);