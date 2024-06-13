import { distance } from "../../services/resources.js";

describe('distance', () => {
    it('calcule la distance qui doit être inférieur à 5 pour récupérer la potion', () => {
        const point1 = [48.858844, 2.294351];
        const point2 = [48.858844, 2.294351];
        const dist = distance(point1[0], point1[1], point2[0], point2[1]);
        expect(dist).toBeLessThan(20);
    });
});