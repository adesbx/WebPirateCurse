import { setZRR } from "../../services/admin.js";
import { getAllPosition } from "../../services/zrr.js";

let saveZrrPosition = "";

describe('getZRR', () => {
    it('lit le fichier zrrdata.json', async () => {
        saveZrrPosition = await getAllPosition();
        expect(saveZrrPosition).not.toBe("");
    });
});

describe('setZRR', () => {
    it('écrit le fichier zrrdata.json', async () => {
        await setZRR({
            "positionNO": {
                "lat": 45.78083940972196,
                "lng": 4.845499340455471
            },
            "positionNE": {
                "lat": 45.78083940972196,
                "lng": 4.850064460198818
            },
            "positionSE": {
                "lat": 45.78009117051412,
                "lng": 4.850064460198818
            },
            "positionSO": {
                "lat": 45.78009117051412,
                "lng": 4.845499340455471
            }
        });
        const zrrPosTmp = await getAllPosition();
        expect(zrrPosTmp).toEqual({
            "positionNO": {
                "lat": 45.78083940972196,
                "lng": 4.845499340455471
            },
            "positionNE": {
                "lat": 45.78083940972196,
                "lng": 4.850064460198818
            },
            "positionSE": {
                "lat": 45.78009117051412,
                "lng": 4.850064460198818
            },
            "positionSO": {
                "lat": 45.78009117051412,
                "lng": 4.845499340455471
            }
        });
    });
});
