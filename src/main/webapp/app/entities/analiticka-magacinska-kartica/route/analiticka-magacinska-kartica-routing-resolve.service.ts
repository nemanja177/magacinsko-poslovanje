import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAnalitickaMagacinskaKartica } from '../analiticka-magacinska-kartica.model';
import { AnalitickaMagacinskaKarticaService } from '../service/analiticka-magacinska-kartica.service';

@Injectable({ providedIn: 'root' })
export class AnalitickaMagacinskaKarticaRoutingResolveService implements Resolve<IAnalitickaMagacinskaKartica | null> {
  constructor(protected service: AnalitickaMagacinskaKarticaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnalitickaMagacinskaKartica | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((analitickaMagacinskaKartica: HttpResponse<IAnalitickaMagacinskaKartica>) => {
          if (analitickaMagacinskaKartica.body) {
            return of(analitickaMagacinskaKartica.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
