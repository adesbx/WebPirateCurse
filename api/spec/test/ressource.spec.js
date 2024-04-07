import { verifyUserExist, grabPotion, getAllresources, terminatePirate, modifyPosition } from "../../services/resources.js";
import { spawnFlask } from "../../services/admin.js";
describe('verify exist', () => {
    it('verify if admin exist', async () => {
        const response = await verifyUserExist("ADMIN");
        expect(response).toBe(true);
    });
});

describe('grab potion', () => {
    it('admin grabPotion', async () => {
        const allRessourcesB = await getAllresources();
        const userB = allRessourcesB.find(obj => obj.id === "ADMIN");
        await grabPotion("ADMIN", "potionTest");
        const potionB = userB.potions
        const allRessources = await getAllresources();
        const userA = allRessources.find(obj => obj.id === "ADMIN");
        
        expect(userA.potions).toBe(potionB + 1);
    });
});

describe('modify position', () => {
    it('modify position', async () => {
        await modifyPosition("ADMIN",[
                "45.78220619944917",
                "4.865867793560029"
        ]);
        const allRessources = await getAllresources();
        const user = allRessources.find(obj => obj.id === "ADMIN");

        expect(user.position).toEqual([
            "45.78220619944917",
            "4.865867793560029"
    ]);

    });
});

// describe('destroy potion', () => {
//     it('admin grabPotion', async () => {
//         await spawnFlask({
//             "id": "potionTest",
//             "position": [
//                 "45.78220619944917",
//                 "4.865867793560029"
//             ],
//             "role": "FLASK",
//             "ttl": 50,
//             "potions": 0,
//             "terminated": 0,
//             "turned": 0
//         })
//         await grabPotion("ADMIN", "potionTest");

//         const allRessources = await getAllresources();
//         const flaskTest = allRessources.find(obj => obj.id === "potionTest");
//         expect(flaskTest).not().toBeDefined();
//     });
// });

// describe('terminatePirate', () => {
//     it('Trying to kill a pirate', async () => {
//         await spawnFlask({
//             "id": "userTestPirate",
//             "position": [
//                 "45.78220619944917",
//                 "4.865867793560029"
//             ],
//             "role": "PIRATE",
//             "ttl": 50,
//             "potions": 0,
//             "terminated": 0,
//             "turned": 0
//         });
//         await spawnFlask({
//             "id": "userTestVillager",
//             "position": [
//                 "45.78220619944917",
//                 "4.865867793560029"
//             ],
//             "role": "VILLAGER",
//             "ttl": 50,
//             "potions": 1,
//             "terminated": 0,
//             "turned": 0
//         })
//         await terminatePirate("userTestVillager", "userTestPirate");
//         const allRessources = await getAllresources();
//         const user = allRessources.find(obj => obj.id === "userTestVillager");
//         expect(user.terminated).toBe(1);
//     });
// });
