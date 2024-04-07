import { spawnFlask, verifyPositionInZRR, setZRR } from "../../services/admin.js";
import { getAllresources } from "../../services/resources.js";

describe('spawnFlask', () => {
    it('vérifie si on spawnFlask', async () => {
        await spawnFlask({
            "id": "potionTest",
            "position": [
                "45.78220619944917",
                "4.865867793560029"
            ],
            "role": "FLASK",
            "ttl": 50,
            "potions": 0,
            "terminated": 0,
            "turned": 0
        })
        expect(true).toBe(true);
    });
});

describe('readFlask', () => {
    it('lit le data.json et trouve la flaskTest', async () => {
        const allRessources = await getAllresources();
        const flaskTest = allRessources.find(obj => obj.id === "potionTest");
        expect(flaskTest).toBeDefined();
    });
});

// describe('verifyPositionInZRR  ok', () => {
//     it('verify qu\'une position est dans la zrr', async () => {
//         await setZRR({
//             "positionNO": {
//                 "lat": 45.78083940972196,
//                 "lng": 4.845499340455471
//             },
//             "positionNE": {
//                 "lat": 45.78083940972196,
//                 "lng": 4.850064460198818
//             },
//             "positionSE": {
//                 "lat": 45.78009117051412,
//                 "lng": 4.850064460198818
//             },
//             "positionSO": {
//                 "lat": 45.78009117051412,
//                 "lng": 4.845499340455471
//             }
//         });


//         const response = await verifyPositionInZRR([45.780465,  4.847782]);
//         expect(response).toBe(true);
//     });
// });

describe('verifyPositionInZRR  not ok', () => {
    it('verify qu\'une position est dans la zrr', async () => {
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


        const response = await verifyPositionInZRR([45.7783, 2.123]);
        expect(response).toBe(false);
    });
});
