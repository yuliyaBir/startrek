"use strict"
import {toon, byId, setText, verwijderChildElementenVan, verberg} from "./util.js";


verbergFout();
const werknemersList = byId("werknemersList");
addWerknemersToList();
function verbergFout(){
    verberg("storing");
}

async function addWerknemersToList(){
    const response = await fetch ("werknemers");
    if (response.ok){
        const wernemers = await response.json();
        for (const werknemer of wernemers){
        let li = document.createElement("li");
        let hyperlink = document.createElement("a");
        hyperlink.innerText = `${werknemer.voornaam} ${werknemer.familienaam}`;
        hyperlink.href = "eenWerknemer.html";
        hyperlink.dataset.nummer = werknemer.id;
        hyperlink.onclick = function (){
                const nummer = this.dataset.nummer;
                sessionStorage.setItem("nummerWerknemer", nummer);
            }
        li.appendChild(hyperlink);
        werknemersList.appendChild(li);}
    } else {
        toon("storing");
    }
}
