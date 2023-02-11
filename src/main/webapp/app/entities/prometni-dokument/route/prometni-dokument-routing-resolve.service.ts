import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPrometniDokument } from '../prometni-dokument.model';
import { PrometniDokumentService } from '../service/prometni-dokument.service';

@Injectable({ providedIn: 'root' })
export class PrometniDokumentRoutingResolveService implements Resolve<IPrometniDokument | null> {
  constructor(protected service: PrometniDokumentService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPrometniDokument | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((prometniDokument: HttpResponse<IPrometniDokument>) => {
          if (prometniDokument.body) {
            return of(prometniDokument.body);
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
