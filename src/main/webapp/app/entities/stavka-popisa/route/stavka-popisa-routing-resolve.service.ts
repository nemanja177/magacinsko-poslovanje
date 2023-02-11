import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IStavkaPopisa } from '../stavka-popisa.model';
import { StavkaPopisaService } from '../service/stavka-popisa.service';

@Injectable({ providedIn: 'root' })
export class StavkaPopisaRoutingResolveService implements Resolve<IStavkaPopisa | null> {
  constructor(protected service: StavkaPopisaService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IStavkaPopisa | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((stavkaPopisa: HttpResponse<IStavkaPopisa>) => {
          if (stavkaPopisa.body) {
            return of(stavkaPopisa.body);
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
