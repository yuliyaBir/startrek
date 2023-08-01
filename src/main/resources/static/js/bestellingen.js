"use strict"
import {byId, toon, setText, verwijderChildElementenVan, verberg} from "./util.js";

const werknemerNaam = sessionStorage.getItem("werknemerNaam");
const werknemerId = Number(sessionStorage.getItem("nummerWerknemer"));
if (werknemerNaam !== null) {
    setText("werknemerTitel", werknemerNaam);
    setText("werknemerLink", werknemerNaam);
}
findByWerknemerId(werknemerId);
async function findByWerknemerId(werknemerId){
    const response = await fetch(`bestellingen/${werknemerId}`);
    if (response.ok){
        const bestellingen = await response.json();
        const bestellingenBody = byId("bestellingenBody");
        verwijderChildElementenVan(bestellingenBody);
        for (const bestelling of bestellingen) {
            const tr = bestellingenBody.insertRow();
            tr.insertCell().innerText = bestelling.id;
            tr.insertCell().innerText = bestelling.omschrijving;
            tr.insertCell().innerText = bestelling.bedrag;
            tr.insertCell().innerText =
                new Date(bestelling.moment).toLocaleString("nl-BE");
        }
        toon("bestellingenBody");
    } else {
        if (response.status === 404) {
            byId("nietGevonden").innerText = "De bestellingen niet gevonden";
            toon("nietGevonden");
        } else {
            toon("storing");
        }
    }
}



