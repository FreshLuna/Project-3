import { writable } from "svelte/store";

// location dropdown script: Aalborg Centrum, Aalborg Øst, Hasseris, Skalborg, Gug, Aalborg Vestby ---- let locationsOpen = false;
export const locations = writable([
    { id: 1, label: "Aalborg Centrum", checked: false },
    { id: 2, label: "Aalborg Øst", checked: false },
    { id: 3, label: "Hasseris", checked: false },
    { id: 4, label: "Skalborg", checked: false },
    { id: 5, label: "Gug", checked: false },
    { id: 6, label: "Aalborg Vestby", checked: false },
]);

// date (day) dropdown script: Mandag, Tirsdag, Onsdag, Torsdag, Fredag, Lørdag, Søndag ---- let weekdayOpen = false;
export const weekdays = writable([
    { id: 1, label: "Mandag", checked: false },
    { id: 2, label: "Tirsdag", checked: false },
    { id: 3, label: "Onsdag", checked: false },
    { id: 4, label: "Torsdag", checked: false },
    { id: 5, label: "Fredag", checked: false },
    { id: 6, label: "Lørdag", checked: false },
    { id: 7, label: "Søndag", checked: false }
]);

// age dropdown script: 0+, 12+, 15+, 18+, 21+, 25+, 30+ ---- let ageOpen = false;
export const ages = writable([
    { id: 1, label: "Alle aldre", checked: false },
    { id: 2, label: "12+", checked: false },
    { id: 3, label: "15+", checked: false },
    { id: 4, label: "18+", checked: false },
    { id: 5, label: "21+", checked: false },
    { id: 6, label: "25+", checked: false },
    { id: 7, label: "30+", checked: false }
]);

// gender dropdown script: let genderOpen = false;
export const genders = writable([
    { id: 1, label: "Alle", checked: false },
    { id: 2, label: "Drenge/mænd", checked: false },
    { id: 3, label: "Piger/kvinder", checked: false }
]);

// tags dropdown script: let tagOpen = false;
export const tags = writable([
    { id: 1, label: "Kampsport", checked: false },
    { id: 2, label: "Vand", checked: false },
    { id: 3, label: "Ketchersport", checked: false }
]);