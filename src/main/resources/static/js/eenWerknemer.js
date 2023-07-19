"use strict"
import {byId, verberg, toon, verwijderChildElementenVan, setText} from "./util.js";


const naam= byId("naam");
const id = Number(sessionStorage.getItem("nummerWerknemer"));
findById(id);

function verbergFoutenEnWerknemer(){
    verberg("storing");
    verberg("notFound");
    verberg("info");
}

async function findById(id) {
    const response = await fetch(`werknemers/${id}`);
    if (response.ok) {
        const werknemer = await response.json();
        console.log(response);
        setText("naam", `${werknemer.voornaam} ${werknemer.familienaam}`);
        const imgFoto = byId("foto");
        imgFoto.alt = naam;
        imgFoto.src = `images/${werknemer.id}.jpg`;
        setText("nummer", JSON.stringify(werknemer.id));
        setText("budget", werknemer.budget);
    } else {
        toon("storing");
    }
}