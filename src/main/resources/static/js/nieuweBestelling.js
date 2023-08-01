import {toon, setText, verwijderChildElementenVan, verberg, byId} from "./util.js";

const werknemerNaam = sessionStorage.getItem("werknemerNaam");
const werknemerId = Number(sessionStorage.getItem("nummerWerknemer"));
setText("werknemerTitel", werknemerNaam);
setText("werknemerLink", werknemerNaam);

byId("bestel").onclick = async function (){
    verbergFouten();
    const omschrijvingInput = byId("omschrijving");
    const bedragInput = byId("bedrag");
    if (!omschrijvingInput.checkValidity()){
        toon("omschrijvingFout");
    }
    if (!bedragInput.checkValidity()){
        toon("bedragFout");
    }
    const nieuweBestelling = {
        omschrijving: omschrijvingInput.value,
        bedrag: bedragInput.value
    };
    omschrijvingInput.value = "";
    bedragInput.value = "";
    create(nieuweBestelling);
}

function verbergFouten(){
    verberg("omschrijvingFout");
    verberg("bedragFout");
    verberg("onvoldoendeBudget");
}
async function create(nieuweBestelling){
    const response = await fetch(`bestellingen/${werknemerId}/nieuweBestelling`,
        {
            method: "POST",
            headers: {
                'Content-Type': "application/json"},
            body: JSON.stringify(nieuweBestelling)
        });
    if (response.ok){
        window.location = "bestellingen.html"
    }else{
        if (response.status === 409)      {
            const responseBody = await response.json();
        setText("onvoldoendeBudget", responseBody.message);
        toon("onvoldoendeBudget");
        } else{
            toon("storing");
        }
    }
}
